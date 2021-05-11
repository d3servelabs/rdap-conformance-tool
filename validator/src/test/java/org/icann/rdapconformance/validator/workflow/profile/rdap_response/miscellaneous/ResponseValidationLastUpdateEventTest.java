package org.icann.rdapconformance.validator.workflow.profile.rdap_response.miscellaneous;

import static org.assertj.core.api.Assertions.assertThat;

import org.icann.rdapconformance.validator.workflow.profile.rdap_response.TopMostEventActionValidationTest;
import org.icann.rdapconformance.validator.workflow.rdap.RDAPQueryType;
import org.testng.annotations.Test;

public class ResponseValidationLastUpdateEventTest extends
    TopMostEventActionValidationTest<ResponseValidationLastUpdateEvent> {

  protected ResponseValidationLastUpdateEventTest() {
    super("rdapResponseProfile_2_3_1_3_and_2_7_6_and_3_3_and_4_4_Validation",
        ResponseValidationLastUpdateEvent.class);
  }

  @Test
  public void testDoLaunch() {
    queryType = RDAPQueryType.HELP;
    assertThat(getTigValidation().doLaunch()).isFalse();
    queryType = RDAPQueryType.NAMESERVERS;
    assertThat(getTigValidation().doLaunch()).isFalse();
    queryType = RDAPQueryType.DOMAIN;
    assertThat(getTigValidation().doLaunch()).isTrue();
    queryType = RDAPQueryType.NAMESERVER;
    assertThat(getTigValidation().doLaunch()).isTrue();
    queryType = RDAPQueryType.ENTITY;
    assertThat(getTigValidation().doLaunch()).isTrue();
  }
}