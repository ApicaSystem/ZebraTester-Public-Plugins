# GenerateRandomCharacters
Generates a defined number of random characters, this can be used for faking ios deviceid for example.
This plugin has been tested with ZebraTester 5.4-D but should work for any 5.2-X version.  

###Installation###
1. Add plugin to ../MyTests/Plugins/
2. Compile plugin via Project Navigator in the Plugins folder

###Usage###
1. Load your ZebraTester scenario
2. Add your input file to the scenario
5. Add plugin "Generate Random Characters" to test
6. Press continue
7. Configure Run-Time Input/Output Parameter's by tying the variables you created previusly as below
  * Number of Characters = This defines how many characters you want to generate.
  * Random Characters = This is the output variable, connect to the value you want to rewrite.
9. Zip togheter the generated class file togheter with your ntlmauth.txt and you are ready to use your test

##About##
Created by Apica  
Maintained by Daniel Freij  

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
