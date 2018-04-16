# visma-net-proxy


visma-net-proxy is using component *visma-net-v1client* for REST-based access to Visma.net API.

visma-net-proxy uses Eclipse-dependency to visma-net-v1client project.

## Generate visma-net-v1client with swagger-codegen


		swagger-codegen generate  \
		     -i https://integration.visma.net/API-index/doc/swagger \
		     -l java \
		     -c <path>/git/visma-net-proxy/swagger-java-config.json \
		     -o <path>/git/visma-net-v1client \
		     --instantiation-types array=ArrayList,map=HashMap \
		     --type-mappings array=List,map=Map,string=String
 
     
Example:

		swagger-codegen generate  \
		     -i https://integration.visma.net/API-index/doc/swagger \
		     -l java \
		     -c /Users/fredrikmoller/git/visma-net-proxy/swagger-java-config.json \
		     -o /Users/fredrikmoller/git/visma-net-v1client \
		     --instantiation-types array=ArrayList,map=HashMap \
		     --type-mappings array=List,map=Map,string=String

 More info on swagger: https://swagger.io/docs/swagger-tools/#installation-11