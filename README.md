# visma-net-proxy
The visma.net proxy

## Generate client with swagger-codegen

More info: https://swagger.io/docs/swagger-tools/#installation-11

		swagger-codegen generate  \
		     -i https://integration.visma.net/API-index/doc/swagger \
		     -l java \
		     -c /<path>/swagger-java-config.json \
		     -o /<path>
		     --instantiation-types array=ArrayList,map=HashMap \
		     --type-mappings array=List,map=Map,string=String
 
     
Example:

		swagger-codegen generate  \
		     -i https://integration.visma.net/API-index/doc/swagger \
		     -l java \
		     -c /Users/fredrikmoller/git/visma-net-proxy/swagger-java-config.json \
		     -o /Users/fredrikmoller/Temp/vismanetapi \
		     --instantiation-types array=ArrayList,map=HashMap \
		     --type-mappings array=List,map=Map,string=String