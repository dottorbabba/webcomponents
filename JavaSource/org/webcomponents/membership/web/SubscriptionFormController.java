package org.webcomponents.membership.web;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.webcomponents.membership.DuplicatedUsernameException;
import org.webcomponents.membership.MemberStatus;
import org.webcomponents.membership.SubscriptionService;
import org.webcomponents.web.servlet.mvc.CaptchaFormController;


@Controller
@SessionAttributes("subscription")
public class SubscriptionFormController extends CaptchaFormController {
	
	protected SubscriptionService subscriptionService;
	protected int type = 0;

	@ModelAttribute("subscription")
	public SubscriptionCommand getCommand() {
		SubscriptionCommand rv = new SubscriptionCommand();
		rv.setType(type);
		rv.setStatus(MemberStatus.ACTIVE);
		return rv;
	}

	@RequestMapping(method=RequestMethod.GET)
	public Map<String, Object> setupForm() {
		return Collections.emptyMap();
	}

	@RequestMapping(method = RequestMethod.POST)
	public void processSubmit(@ModelAttribute("subscription") SubscriptionCommand subscription, Errors errors, HttpSession session) throws Exception {
		validateCaptcha(subscription, errors, session);
		validateCommand(subscription, errors);
		if(errors.hasErrors()) {
			return;
		}
		try {
			subscriptionService.subscribe(subscription);
		} catch (DuplicatedUsernameException e) {
			errors.reject("duplicate");
		}
	}

	public final void setSubscriptionService(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	public final void setType(int type) {
		this.type = type;
	}
}
