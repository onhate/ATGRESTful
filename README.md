ATGRESTfull
===========

Well, in my opinion native Oracle ATG RESTful API is really deprecated, complicated and strange. So, I created this module.

An [Oracle ATG](http://www.oracle.com/us/products/applications/commerce/atg/index.html) powered with a great RESTful Framework [Jersey](https://jersey.java.net/).

How does Oracle ATG RESTful module works? Easy and simple like this:

```Java
@Path("/atg-restful")
public class RequestService extends BaseRESTService {

    @Nucleus("/OriginatingRequest")
	private HttpServletRequest request;

	@Nucleus("/atg/userprofiling/Profile")
	private Profile profile;

	@Nucleus("/atg/commerce/catalog/ProductCatalog")
	private Repository repository;

	@GET
	@Path("/test")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	public Response getExample(@PathParam("name") String name) {
		String contextPath = request.getContextPath();
		String profileName = profile.getName();
		String repositoryName = repository.getRepositoryName();

		Map<String, Object> result = createResult();
		result.put("param", name);
		result.put("requestScope", contextPath);
		result.put("sessionScope", profileName);
		result.put("globalScope", repositoryName);

		return Response.ok(result).build();
	}
}
```

Then, a simple GET on path /atg-restful/test URL, will return a JSON/XML/TXT with a param name value, a request, session ang global scoped component value.


How to use it?
===========

On your ```MANIFEST.MF``` just require the module ```Texelz.RESTful``` like this:
```
ATG-Required: ... Texelz.RESTful ...
```

Then, on your ```web.xml``` file place this code:

```XML
<servlet>
	<servlet-name>NucleusJerseyREST</servlet-name>
	<servlet-class>com.texelz.atgrestful.NucleusJerseyServlet</servlet-class>
	<init-param>
		<param-name>com.sun.jersey.config.property.resourceConfigClass</param-name>
		<param-value>com.texelz.atgrestful.NucleusResourceConfig</param-value>
	</init-param>
	<init-param>
		<param-name>com.sun.jersey.api.json.POJOMappingFeature</param-name>
		<param-value>true</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>NucleusJerseyREST</servlet-name>
	<url-pattern>/r/*</url-pattern>
</servlet-mapping>
```

Jersey works loading a package with ```@Path``` annotated, so, now you just need to configure the component ```/com/texelz/atgrestful/NucleusRestPackages``` like this:

```INI
packages+=\
  #you packages...
  com.custom.restservices.MyService
```




P.S.:
=====

It was used some classes and ideas from [Stormpath - todos-jersey](https://github.com/stormpath/todos-jersey) example :). 

Thanks.
