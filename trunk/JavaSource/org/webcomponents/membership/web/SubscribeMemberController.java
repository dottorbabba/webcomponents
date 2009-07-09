package org.webcomponents.membership.web;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.webcomponents.membership.MemberStatus;
import org.webcomponents.membership.Membership;
import org.webcomponents.web.servlet.mvc.CaptchaFormController;

@Controller
@SessionAttributes("subscription")
public class SubscribeMemberController extends CaptchaFormController {
	
	private Membership membership;
	
	@RequestMapping(method = RequestMethod.GET)
	@ModelAttribute("subscription")
	public SubscribeMemberCommand setupForm() {
		SubscribeMemberCommand rv = new SubscribeMemberCommand();
		rv.setStatus(MemberStatus.PENDING);
		return rv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("subscription") SubscribeMemberCommand command, Errors errors, HttpSession session) throws IOException {
		validateCaptcha(command, errors, session);
		validateCommand(command, errors);
		if(errors.hasErrors()) {
			return null;
		}
		try {
			membership.insertMember(command, command.getPassword());
			return successView;
		} catch (BindException e) {
			errors.addAllErrors(e);
			return null;
		}
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}
	
}
