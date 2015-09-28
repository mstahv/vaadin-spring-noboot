package org.test;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Theme("valo")
@SpringUI
@SuppressWarnings("serial")
public class MyUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    public static class Servlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }

    @Autowired
    SpringViewProvider viewProvider;

    @Service
    public static class MyService {

        public String sayHi() {
            return "Hello Vaadin user!";
        }

    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.navigateTo(DefaultView.NAME);
    }

    @SpringComponent
    @SpringView(name = DefaultView.NAME)
    public static class DefaultView extends VerticalLayout implements View {

        public final static String NAME = "defaultView";

        @Autowired
        MyService myService;
        
        @PostConstruct
        protected void init() {
            addComponent(new Label(myService.sayHi()));
        }
        
        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
        }

    }
}
