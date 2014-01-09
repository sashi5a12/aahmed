package com.netpace.vic.servlet;

import static nl.captcha.Captcha.NAME;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.text.renderer.ColoredEdgesWordRenderer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates captcha image to tell whether its user is a human or a computer.
 */
@Component(immediate = true, metatype = false)
@Service(value = javax.servlet.Servlet.class)
@Properties({
    @Property(name = "service.description", value = "Servlet to generate captcha"),
    @Property(name = "service.vendor", value = "Netpace"),
    @Property(name = "sling.servlet.paths", value = {"/bin/getCaptcha"
    })
})

public class CaptchaServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static int _width = 200;
    private static int _height = 50;

    private static final List<Color> COLORS = new ArrayList<Color>(2);
    private static final List<Font> FONTS = new ArrayList<Font>(3);

    static {
        COLORS.add(Color.BLACK);
        COLORS.add(Color.BLUE);

        FONTS.add(new Font("Geneva", Font.ITALIC, 48));
        FONTS.add(new Font("Courier", Font.BOLD, 48));
        FONTS.add(new Font("Arial", Font.BOLD, 48));
    }

    @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp)
            throws ServletException, IOException {
        ColoredEdgesWordRenderer wordRenderer = new ColoredEdgesWordRenderer(COLORS, FONTS);
        Captcha captcha = new Captcha.Builder(_width, _height).addText()
                //                .gimp()
                //                .gimp(new DropShadowGimpyRenderer())
                .addBackground(new FlatColorBackgroundProducer(Color.GRAY))
                //                .addBorder()
                .build();

        req.getSession().setAttribute(NAME, captcha);

        LOGGER.info("Captcha Word: " + captcha.getAnswer());
        String encode64=req.getParameter("IS_ENCODE64");
        BufferedOutputStream output = null;
        ByteArrayOutputStream baos = null;
        try {
            output = new BufferedOutputStream(resp.getOutputStream());
            baos = new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(), "jpg", baos);
            baos.flush();
            byte[] imageInByte=null;
            if(StringUtils.isNotEmpty(encode64)){                
                imageInByte = Base64.encodeBase64(baos.toByteArray());
            }
            else {
                imageInByte = baos.toByteArray();
            }
            output.write(imageInByte);

        } catch (IOException e) {
            System.out.println("There are errors in reading/writing image stream "
                    + e.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ignore) {
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException ignore) {
                }
            }
        }

    }
}
