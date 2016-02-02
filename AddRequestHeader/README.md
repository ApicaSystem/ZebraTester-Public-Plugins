# Add Request Header
Adds custom request header to url requests.
Compatible with 5.1C and higher versions. 

###Installation###
1. Add plugin to ../MyTests/Plugins/
2. Compile plugin via Project Navigator in the Plugins folder

###Usage###
Plug-in Class: AddRequestHeader
 Last Modified: 	 27 Jan 2016 21:16:02
 Plug-in Type: 	 Load Test Plug-in
 Plug-in Name: 	 Add a Request Header
 Construct Scope: 	 not fixed - arbitrary
 Execution Scope: 	 not fixed - arbitrary
 Execution Order: 	 not fixed - arbitrary
 Allow Multiple Usage: 	 yes

Input Parameter
 No. 	 Name 	 Utilization 	 Assign From
 1 	 Request Header Name 	 mandatory 	 variable
 2 	 Request Header Value 	 mandatory 	 variable

Output Parameter
[none]

Create vars for request header name and value.
example:
Request Header Name - Authorization
Request Header Value - Basic 7a6cb6e7f9d3256

##About##
Created by Apica  
Maintained by Ervin Talactac

##License##
The MIT License (MIT)

Copyright (c) [2015] [Apica Inc]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.