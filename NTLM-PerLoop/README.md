# NTLM-PerLoop
Changes ZebraTester Behavior from NTLM Auth per User to NTLM Auth per Loop.  
This plugin has been tested with ZebraTester 5.2-E and 5.2-M but should work for any 5.2-X version.  

###Installation###
1. Add plugin to ../MyTests/Plugins/
2. Compile plugin via Project Navigator in the Plugins folder

###Usage###
1. Load your ZebraTester scenario
2. Create ntlmauth.txt in your scenario directory with the following structure,   
```
#ntlmDomain;ntlmUsername;ntlmPassword  
domain.local;username1;password1
```
3. Add your input file to the scenario
4. Create the following variables tied to the columns of ntlmauth.txt
  * ntlmDomain
  * ntlmUsername
  * ntlmPassword
5. Add plugin "NTLM per Loop" to test
6. Press continue
7. Configure Run-Time Input Parameter by tying the variables you created previusly as below
  * Domain = ntlmDomain
  * Username = ntlmUsername
  * Password = ntlmPassword
8. When compiling your test make sure to select "apply individual NTLM account per user from input file ntlmauth.txt"
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