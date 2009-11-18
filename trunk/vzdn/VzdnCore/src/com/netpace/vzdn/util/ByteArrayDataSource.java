package com.netpace.vzdn.util;
import javax.activation.DataSource;
import java.io.*;

public class ByteArrayDataSource implements DataSource
{
   byte[] bytes;
   String contentType, name;

   ByteArrayDataSource(byte[] bytes,
                       String name,
                       String contentType)
   {
      this.bytes = bytes;
      if(contentType == null)
         this.contentType = "application/octet-stream";
      else
         this.contentType = contentType;
      this.name = name;
   }

   public String getContentType()
   {
      return contentType;
   }

   public InputStream getInputStream()
   {
      // remove the final CR/LF
      return new ByteArrayInputStream(
         bytes,0,bytes.length - 2);
   }

   public String getName()
   {
      return name;
   }

   public OutputStream getOutputStream()
      throws IOException
   {
      throw new FileNotFoundException();
   }
}
