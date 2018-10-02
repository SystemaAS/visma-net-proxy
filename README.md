# visma-net-proxy

visma-net-proxy is using component *visma-net-v1client* for REST-based access to Visma.net API.

visma-net-proxy uses Eclipse-dependency to project:
* visma-net-v1client
* espedsgcommon
* syjservicescommon


## Create new visma-net-vXXXclient

0. Ensure swagger-codegen is installed. See [Swagger](https://swagger.io/docs/open-source-tools/swagger-codegen/)
1. Init repo in Git. 
2. Import repo to Eclipse
2. Create swagger-java-config.json. See [previous version](https://github.com/SystemaAS/visma-net-v1client/blob/master/swagger-java-config.json)
3. Generate client into repo. See [previous version](https://github.com/SystemaAS/visma-net-v1client/blob/master/README.md)
4. Remove dependency to [previous version](https://github.com/SystemaAS/visma-net-v1client)
4. Add dependency to visma-net-vXXXclient to visma-net-proxy.
5. Run appropriate tests.
