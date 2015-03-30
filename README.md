resthelper
==========

A useful helper servlet for REST based web apps in javascript.

What?
-----

Resthelper is a simple servlet which you can use to add application 
keys to your rest requests without exposing them in the javascript code.

How?
----

Resthelper needs these configuration information:

  * the URL of the REST server
  * the applicationKey of the application using the REST service

Resthelper stores these information in the file:

	CONFIG-BASE-DIR/YOUR-APP-ID/app.properties

(see below on how to configure CONFIG-BASE-DIR and YOUR-APP-ID)

Then instead of accessing the REST server directly you send all requests
to this servlet which will add the application keys and relay the request
to the original server.

Why?
----

REST applications sometimes require an application key to be sent in the
request header. It would be hard to keep that header in the javascript
code and not make it visible to the outside.

This also circumvents the CORS problem that prevents your javascript
from sending requests to any other server than your original server.

These projects use resthelper:

  * https://github.com/elelpublic/infomarket
  * https://github.com/elelpublic/flyer

How to use and configure it?
----------------------------

Create a webapp and adapt the servlets and filters from web.xml of resthelper.
Read the comments in the web.xml on how to configure resthelper, 
then change the init-params to your needs.

To configure the production instance of your application, change the
URL and application key in the file:

	CONFIG-BASE-DIR/YOUR-APP-ID/app.properties






