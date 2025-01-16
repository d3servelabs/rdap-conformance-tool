# RDAP Conformance Tool (rdapct)

The RDAP Conformance Tool is a stand-alone tool acting like a test suite that verifies the conformity of an RDAP server against 
the specifications developed by the IETF (RFC7481, RFC7482, RFC7483, RFC7484) and the ICANN gTLD RDAP
profile (https://www.icann.org/gtld-rdap-profile). It only tests RDAP servers related to domains. 
Apart from generic RDAP tests, there are no specific tests for IP addresses and AS Numbers RDAP servers.

# Installing and Using

Instructions for installing and using this software can be found [here](https://github.com/icann/rdap-conformance-tool/wiki).
This wiki also includes explanations for some of the common conformance issues.

# General Structure

This tool implements more than 300 tests, as detailed in the doc directory.

The tool is divided into two modules with their respective documentation:

- [Command line module (tool)](./tool/README.md) the main entry point
- [Validator module](./validator/README.md) the rdap validation library

# Configuration file

Configuration definition
The definition file specifies which single tests are errors, warnings, or ignored (i.e. not tested for).
Note: a definition file is required for starting a test.
A configuration definition file specifies:

- definitionIdentifier: a required JSON string that identifies the configuration definition file.
  The string is copied verbatim to the definitionIdentifier element of the results file.
- definitionError: an optional JSON array of objects.
    - Each object contains the following elements:
        - code: a required JSON number that identifies a single test.
        - notes: a required JSON string that is copied verbatim if the test fails, generating an
          entry in the results section in the results file.
- definitionWarning: an optional JSON array of objects.
    - Each object contains the following elements:
        - code: a required JSON number that identifies a single test.
        - notes: an optional JSON string that is copied verbatim if the test fails, generating an
          entry in the results section in the results file.
- definitionIgnore: an optional JSON array of single test identifiers that are ignored (i.e. not
  tested for). The contents of this element are copied verbatim to the ignore section in the results
  file.
- definitionNotes: an optional JSON array of strings that are copied verbatim to the notes section
  in the results file.

## Minimal config file

The simplest config file one can write looks like this:

```
{
    "definitionIdentifier": "test"
}
```

This config file will simply tells the tool to put the id "test" inside the output result file. This
id allows the user to discriminate test runs between each other.

## A more complete example

A more complete example of a configuration definition file is shown below:

```
{
    "definitionIdentifier": "Sample rdap-conformance-tool configuration file",
    "definitionWarning": [
       { "code": -12308,
         "notes": "Entity includes invalid Entities array: See errors for details." },
       { "code": -12309,
         "notes": "Entity includes invalid Remarks array: See errors for details." },
       { "code": -12213,
         "notes": "Domain includes invalid Remarks object; See errors for details." },
       { "code": -12210,
         "notes": "Domain includes invalid Entities array; See errors for details." },
       { "code": -11901,
         "notes": "Entities array includes invalid Entity: See errors for details." },
       { "code": -12214,
         "notes": "Domain includes invalid Links array: See errors for details." }
    ],
    "definitionIgnore": [],
    "definitionNotes": ["This is a custom configuration definition to filter hierarchical errors as warnings."]
}
```

# How to build this project

This is a maven project: to build the executable jar simply run

    mvn package -DskipTests

# Requirements

- Java 21

# Internals

This software fetches various datasets to be used for doing the tests. These datasets are fetched from IANA registries,
saved into the datasets directory, and parsed by the various tests as needed.
The datasets are:
- RDAPExtensionsDataset: https://www.iana.org/assignments/rdap-extensions/rdap-extensions.xml
- RegistrarIdDataset: https://www.iana.org/assignments/registrar-ids/registrar-ids.xml
- BootstrapDomainNameSpaceDataset: https://data.iana.org/rdap/dns.json
- EPPRoidDataset: https://www.iana.org/assignments/epp-repository-ids/epp-repository-ids.xml
- SpecialIPv4AddressesDataset: https://www.iana.org/assignments/iana-ipv4-special-registry/iana-ipv4-special-registry.xml
- IPv4AddressSpaceDataset: https://www.iana.org/assignments/ipv4-address-space/ipv4-address-space.xml
- LinkRelationsDataset: https://www.iana.org/assignments/link-relations/link-relations.xml
- RDAPJsonValuesDataset: https://www.iana.org/assignments/rdap-json-values/rdap-json-values.xml
- IPv6AddressSpaceDataset: https://www.iana.org/assignments/ipv6-address-space/ipv6-address-space.xml
- DsRrTypesDataset: https://www.iana.org/assignments/ds-rr-types/ds-rr-types.xml
- DNSSecAlgNumbersDataset: https://www.iana.org/assignments/dns-sec-alg-numbers/dns-sec-alg-numbers.xml
- MediaTypesDataset: https://www.iana.org/assignments/media-types/media-types.xml
- SpecialIPv6AddressesDataset: https://www.iana.org/assignments/iana-ipv6-special-registry/iana-ipv6-special-registry.xml


# Release Process

All pushes and pull requests to the master branch are built using GitHub Actions. GitHub Actions are also used
to create a new version and put it in a [release](https://github.com/icann/rdap-conformance-tool/releases).

To publish, a release:
1. Update the version in the parent `pom.xml` file.
1. Commit that change.
1. Create a tag with the version number such as `v1.0.1` (must be "vX.X.X").
1. Push the tag to GitHub and a GitHub action will build and release the tool.

# License

 Copyright 2021 Internet Corporation for Assigned Names and Numbers ("ICANN")

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 

# Acknowledgements

This RDAP conformance tool has been developed by Viagénie
(Julien Bernard, Guillaume Blanchet, Marc Blanchet, Pierre Larochelle) under a contract from ICANN.

## Namefi Additonal Notes

Build docker image

```sh
docker build -t rdapct .
```

Push to docker hub

```sh
docker push xinbenlv/rdapct
```

Tag docker image

```
docker tag xinbenlv/rdapct:latest xinbenlv/rdapct:1.0.7-patch-1
```

Pull from docker hub
```
docker pull xinbenlv/rdapct
```

Run the test with verbose output using docker.

```sh
docker run --network host -v $(pwd)/config:/app/config --entrypoint java xinbenlv/rdapct:1.0.7-patch-1 -jar /app/tool/target/rdapct-1.0.7.jar -c /app/config/config.json --gtld-registry https://rdap.namefi.io/api/rdap/domain/example.com -v
```
