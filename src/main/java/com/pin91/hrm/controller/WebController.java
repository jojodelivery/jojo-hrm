package com.pin91.hrm.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pin91.hrm.exception.JojoHRMException;
import com.pin91.hrm.service.IUserService;
import com.pin91.hrm.transferobject.EmployeeTO;

@Controller
public class WebController {

	public final static Logger JOJO_LOG = Logger.getLogger(WebController.class.getName());
	@Autowired
	IUserService iUserService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getRootContext() {
		ModelAndView model = new ModelAndView("login");
		return model;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ModelAndView authenticate(@RequestParam final String username, @RequestParam final String password,
			HttpSession session) throws JojoHRMException {

		JOJO_LOG.log(Level.INFO, "Login process starts for username {}", username);
		ModelAndView model = null;
		try {
			EmployeeTO employee = iUserService.validateUser(username, password);
			model = new ModelAndView("home");
			session.setAttribute(EmployeeTO.class.getName(), employee);
			session.setAttribute("LOGIN_USER", true);
		} catch (Exception e) {
			model = new ModelAndView("login");
			model.addObject("ErrorMessage", "The phone number or password you entered is incorrect.");
			JOJO_LOG.log(Level.INFO, "Login process failed for username {}", username);
		}
		return model;
	}

	@RequestMapping(value = "/apply-leave", method = RequestMethod.GET)
	public ModelAndView applyLeaves() {
		ModelAndView model = new ModelAndView("apply-leave");
		return model;
	}

	@RequestMapping(value = "/view-leaves", method = RequestMethod.GET)
	public ModelAndView viewLeaves() {
		ModelAndView model = new ModelAndView("view-leaves");
		return model;
	}

	@RequestMapping(value = "/manager-pending-leaves", method = RequestMethod.GET)
	public ModelAndView managerViewPendingLeaves() {
		ModelAndView model = new ModelAndView("manager-pending-leaves");
		return model;
	}

	@RequestMapping(value = "/fill-timecard", method = RequestMethod.GET)
	public ModelAndView timecardView() {
		ModelAndView model = new ModelAndView("fill-timecard");
		return model;
	}

	@RequestMapping(value = "/view-timecard", method = RequestMethod.GET)
	public ModelAndView viewTimecards() {
		ModelAndView model = new ModelAndView("view-timecard");
		return model;
	}

	@RequestMapping(value = "/manager-view-timecard", method = RequestMethod.GET)
	public ModelAndView managerViewTimecards() {
		ModelAndView model = new ModelAndView("manager-view-timecard");
		return model;
	}

	@RequestMapping(value = "/register-da", method = RequestMethod.GET)
	public ModelAndView registerDA() {
		ModelAndView model = new ModelAndView("register-da");
		return model;
	}

	@RequestMapping(value = "/view-da", method = RequestMethod.GET)
	public ModelAndView viewDA() {
		ModelAndView model = new ModelAndView("view-da");
		return model;
	}

	@RequestMapping(value = "/view-payslip", method = RequestMethod.GET)
	public ModelAndView viewPayslips() {
		ModelAndView model = new ModelAndView("view-payslip");
		return model;
	}

	@RequestMapping(value = "/download-daily-report", method = RequestMethod.GET)
	public ModelAndView viewDailyReport() {
		ModelAndView model = new ModelAndView("download-daily-report");
		return model;
	}

	@RequestMapping(value = "/download-monthly-report", method = RequestMethod.GET)
	public ModelAndView viewMonthlyReport() {
		ModelAndView model = new ModelAndView("download-monthly-report");
		return model;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		ModelAndView model = new ModelAndView("login");
		session.removeAttribute(EmployeeTO.class.getName());
		session.removeAttribute("LOGIN_USER");
		session.invalidate();
		return model;
	}
}
