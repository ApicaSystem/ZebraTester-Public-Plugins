# NTLM-PerLoop
Changes ZebraTester Behavior from NTLM Auth per User to NTLM Auth per Loop.
This plugin has been tested with ZebraTester 5.2-E and 5.2-M but should work for any 5.2-X version.

##Guide##

###Installation###
1. Add plugin to ../MyTests/Plugins/
2. Compile plugin via Project Navigator in the Plugins folder

###Usage###
1. Load your ZebraTester scenario
2. Add input file with your NTLM authentication
  1. Enter auth in this file with Domain;Username;Password columns
3. Create the following variables tied to the input file
  1. ntlmDomain
  2. ntlmUsername
  3. ntlmPassword
4. Add plugin "NTLM per Loop" to test
5. Press continue
6. Configure Run-Time Input Parameter by tying the variables you created previusly as below
  * Domain = ntlmDomain
  * Username = ntlmUsername
  * Password = ntlmPassword
7. When compiling your test make sure to select "apply individual NTLM account per user from input file ntlmauth.txt"

When above has been configured and the test has compiled successfully you will now be able to run your test and this time assign credentials on a per loop basis rather than a per thread basis.

##About##
Created by David F
Maintained by Daniel F


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