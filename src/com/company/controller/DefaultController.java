package com.company.controller;

import com.company.transport.messageSender.MessageSender;
import com.company.view.ViewConfiguration;

public interface DefaultController extends ViewConfiguration {

    MessageSender messageSender = MessageSender.getInstance();

}
