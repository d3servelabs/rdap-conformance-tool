package org.icann.rdapconformance.validator.workflow.profile.tig_section.general;

import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.stream.Collectors;
import org.icann.rdapconformance.validator.workflow.rdap.RDAPValidationResult;
import org.icann.rdapconformance.validator.workflow.rdap.RDAPValidatorResults;

public class Validation1Dot13 implements TigValidation {

  private final HttpResponse<String> rdapResponse;
  private final RDAPValidatorResults results;

  public Validation1Dot13(HttpResponse<String> rdapResponse,
      RDAPValidatorResults results) {

    this.rdapResponse = rdapResponse;
    this.results = results;
  }

  @Override
  public boolean validate() {
    boolean hasError = false;
    Optional<HttpResponse<String>> responseOpt = Optional.of(rdapResponse);
    while (responseOpt.isPresent()) {
      HttpResponse<String> response = responseOpt.get();
      if (!response.headers().allValues("Access-Control-Allow-Origin").contains("*")) {
        results.add(RDAPValidationResult.builder()
            .code(-20500)
            .value(response.headers().map().entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue().toString())
                .collect(Collectors.joining(", ")))
            .message("The HTTP header \"Access-Control-Allow-Origin: *\" is not included in the "
                + "HTTP headers. See section 1.13 of the RDAP_Technical_Implementation_Guide_2_1.")
            .build());
        hasError = true;
      }
      responseOpt = response.previousResponse();
    }
    results.addGroup("tigSection_1_13_Validation", hasError);
    return !hasError;
  }
}
