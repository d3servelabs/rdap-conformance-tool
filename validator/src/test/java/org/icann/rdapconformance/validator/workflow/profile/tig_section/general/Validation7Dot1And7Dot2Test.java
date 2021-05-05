package org.icann.rdapconformance.validator.workflow.profile.tig_section.general;

import com.jayway.jsonpath.JsonPath;
import java.util.Map;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class Validation7Dot1And7Dot2Test extends TigValidationTestBase {

  public Validation7Dot1And7Dot2Test() {
    super("rdap_domain.json",
        "/validators/domain/valid.json",
        "tigSection_7_1_and_7_2_Validation");
  }

  @Test
  public void testValidate_ok() {
    testValidate_ok(new Validation7Dot1And7Dot2(jsonObject.toString(), results));
  }

  /**
   * 8.1.9
   */
  @Test
  public void tigSection_4_1_Validation() {
    // replace the type == voice/fax valid with a wrong value:
    Map<String, String> wrongType = Map.of("type", "not-voice-nor-fax");
    jsonObject = new JSONObject(JsonPath
        .parse(jsonObject.toString())
        .set("$['entities'][0]['entities'][0]['vcardArray'][1][2][1]", wrongType)
        .jsonString());
    validate(new Validation7Dot1And7Dot2(jsonObject.toString(), results), -20900,
        "#/entities/0/entities/0/vcardArray/1/2:{\"type\":\"not-voice-nor-fax\"}",
        "An entity with a tel property without a voice or fax "
            + "type was found. See section 7.1 and 7.2 of the TIG.");
  }
}