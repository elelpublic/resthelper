resthelper
==========

A useful helper servlet for REST based web apps in javascript.

What?
-----

Resthelper is a simple servlet which you can use to add application 
keys to your rest requests without exposing them in the javascript code.

How?
----

Resthelper stores the application keys in a file in your home directory,
usually ~/.YOURAPPNAME.

Then instead of accessing the REST server directly you send all requests
to this servlet which will add the application keys and relay the request
to the original server.

This also circumvents the CORS problem that prevents your javascript
from sending requests to any other server than your original server.

Why?
----

REST applications sometimes require an application key to be sent in the
request header. It would be hard to keep that header in the javascript
code and not make it visible to the outside.

These projects use resthelper:

https://github.com/elelpublic/infomarket
https://github.com/elelpublic/flyer




