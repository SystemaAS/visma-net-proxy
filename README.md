# visma-net-proxy
The Visma.net proxy

## Generate

		swagger-codegen generate  \
		     -i https://integration.visma.net/API-index/doc/swagger \
		     -l java \
		     -c /<path>/swagger-java-config.json \
		     -o /<path>
 
     
Example:

		swagger-codegen generate  \
		     -i https://integration.visma.net/API-index/doc/swagger \
		     -l java \
		     -c /Users/fredrikmoller/git/visma-net-proxy/swagger-java-config.json \
		     -o /Users/fredrikmoller/Temp/VismaNetAPI
