package com.walterjwhite.web.service;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
public class ProtocolVersion {
  protected String majorVersion;
  protected String minorVersion;
}
