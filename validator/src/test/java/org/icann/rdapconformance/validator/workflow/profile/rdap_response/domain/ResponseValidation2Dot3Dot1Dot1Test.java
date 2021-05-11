package org.icann.rdapconformance.validator.workflow.profile.rdap_response.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.icann.rdapconformance.validator.workflow.profile.rdap_response.TopMostEventActionValidationTest;
import org.icann.rdapconformance.validator.workflow.rdap.RDAPQueryType;

public class ResponseValidation2Dot3Dot1Dot1Test extends
    TopMostEventActionValidationTest<ResponseValidation2Dot3Dot1Dot1> {

  public ResponseValidation2Dot3Dot1Dot1Test() {
    super("rdapResponseProfile_2_3_1_1_Validation", ResponseValidation2Dot3Dot1Dot1.class);
  }

  @Override
  public void testDoLaunch() {
    queryType = RDAPQueryType.HELP;
    assertThat(getTigValidation().doLaunch()).isFalse();
    queryType = RDAPQueryType.NAMESERVERS;
    assertThat(getTigValidation().doLaunch()).isFalse();
    queryType = RDAPQueryType.NAMESERVER;
    assertThat(getTigValidation().doLaunch()).isFalse();
    queryType = RDAPQueryType.ENTITY;
    assertThat(getTigValidation().doLaunch()).isFalse();
    queryType = RDAPQueryType.DOMAIN;
    assertThat(getTigValidation().doLaunch()).isTrue();
  }
}