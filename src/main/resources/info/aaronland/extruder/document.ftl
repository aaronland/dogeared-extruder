<#-- @ftlvariable name="" type="info.aaronland.extruder.DocumentView" -->
<!DOCTYPE html> 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><#-- please fix me --></title>
    <style type="text/css">
      body {
      font-size: 1.5em;
      line-height: 1.8em;
      margin: 12%;
      margin-top: 8%;
      }

      p {
      margin-bottom:2em;
      }
    </style>
  </head>
    <body>
      <#-- no really... so... dumb... -->
      <#-- http://stackoverflow.com/questions/3864022/freemarker-assign-list-length-to-local-variable -->					 
      <#assign i = document.getBlocks()?size>

      <#list document.getBlocks() as p>
	<p>${p?html}</p>
      </#list>  
	
    </body>
</html>
