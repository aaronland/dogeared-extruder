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
      text-indent: 4em;
      }
    </style>
  </head>
    <body>
      <#list document.getBlocks() as p>
	<p>${p?html}</p>
      </#list>  
    </body>
</html>
<#-- -*- coding: utf-8 -*- -->
