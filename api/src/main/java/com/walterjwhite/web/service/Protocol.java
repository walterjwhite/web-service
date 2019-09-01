package com.walterjwhite.web.service;

public interface Protocol {
  Class getType();

  ProtocolVersion getVersion();
}
