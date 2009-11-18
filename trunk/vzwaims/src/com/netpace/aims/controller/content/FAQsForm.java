package com.netpace.aims.controller.content;

import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="FAQsForm"
 */

public class FAQsForm extends BaseValidatorForm
{

    private String answer;
    private String question;
    private String faqCategoryId;
    private String faqTopicId;
    private java.util.Collection categoryList;

    public String getFaqTopicId()
    {
        return faqTopicId;
    }
    public void setFaqTopicId(String faqTopicId)
    {
        this.faqTopicId = faqTopicId;
    }
    public String getAnswer()
    {
        return answer;
    }
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    public String getQuestion()
    {
        return question;
    }
    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getFaqCategoryId()
    {
        return faqCategoryId;
    }
    public void setFaqCategoryId(String faqCategoryId)
    {
        this.faqCategoryId = faqCategoryId;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        System.out.println("The RESET is called in Create FAQ Form");
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        ActionErrors errors = new ActionErrors();

        System.out.println("Validation is called in Create FAQ form");

        if (request.getParameter("faqCategoryId") != null && request.getParameter("faqCategoryId").length() < 1)
        {
            System.out.println("Caegory Validated");
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.required", "FAQ Category"));
        }
        if (request.getParameter("question") != null && request.getParameter("question").length() < 1)
        {
            System.out.println("Question Validated");
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.required", "FAQ Question"));
        }
        if (request.getParameter("answer") != null && request.getParameter("answer").length() < 1)
        {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.required", "FAQ Answer"));
            System.out.println("Answer Validated");
        }

        return errors;
    }
    public java.util.Collection getCategoryList()
    {
        return categoryList;
    }
    public void setCategoryList(java.util.Collection categoryList)
    {
        this.categoryList = categoryList;
    }

}