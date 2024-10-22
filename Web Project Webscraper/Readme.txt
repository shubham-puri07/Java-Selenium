UPDATED DATE: 08/01/2024

UPDATES:
1. Updated the version of dependencies in pom.xml to their latest versions.
2. Split code in the classes under testcases package to testcases and baseclass level to follow the current framework structure.
3. Added separate code for loading Default input Excel sheet as the earlier input sheet names are bit confusing due to multiple copies at the same location.
4. Changed the location of output Excel sheets to ExcelOutput Folder in project location.

Please refer the below steps for script execution - 

====================================================================================================================================


HOW TO RUN WebScraperTest ON DIFFERENT ENVIRONMENT:
1. GO TO "testdata" FOLDER AND ADD TEST URLS IN "datasheet_WebScraperVerification.xlsx" EXCEL FILE
2. GO TO "WebScraperVerification.java" FILE
	a) GIVE THE INPUT IN LINE NO. 28 ACCORDINGLY
	b) IF INPUT IS "uat" THEN IT WILL RUN ON UAT ENVIRONMENT
	c) IF INPUT IS "preview" THEN IT WILL RUN ON PRODAUTHOR ENVIRONMENT
	d) IF INPUT IS "live" THEN IT WILL RUN ON LIVE SITE ENVIRONMENT
3. NOW RIGHT CLICK AND CLICK RUN AS > TESTNG TEST
4. AFTER EXECUTION OF THE SCRIPT, RESULT FILE WILL BE GENERATED IN "ExcelOutput > WebscraperVerification" FOLDER (LOOK FOR THE RECENT ONE)
5. IN RESULTS WE NEED TO LOOK FOR "404" STATUS CODE
	a) ONCE WE FIND IT, THEN WE HAVE TO MANUALLY CHECK IF IT IS ACTUAL REDIRECTING TO 404 PAGE
	b) IF YES, THEN WE NEED TO CREATE A BUG FOR THE SAME
	
=====================================================================================================================================

HOW TO RUN JsonValuesTest:
1. GO TO "testdata" FOLDER AND FIND "datasheet_JsonValueVerification.xlsx" EXCEL FILE AND OPEN IT
2. NOW DELETE ALL EXISTING DATA/URLS (deleting all existing data is mandatory) AND ADD NEW TEST URLS AND SAVE IT 
3. GO TO "JsonValuesVerification.java" FILE
4. NOW RIGHT CLICK AND CLICK "Run As > TestNG Test"
5. AFTER EXECUTION OF THE SCRIPT, AGAIN GO TO RESULT FILE WILL BE GENERATED IN "ExcelOutput > JsonValuesVerification" FOLDER (LOOK FOR THE RECENT ONE)

=====================================================================================================================================

HOW TO RUN PdfReaderTest:
1. GO TO "testdata" FOLDER AND ADD TEST URLS AND EXPECTED DATA IN "datasheet_PdfReaderVerification.xlsx" EXCEL FILE
2. GO TO "PdfReaderTest" FILE
3. NOW RIGHT CLICK AND CLICK "Run As > TestNG Test"
4. AFTER EXECUTION OF THE SCRIPT, RESULT FILE WILL BE GENERATED IN "ExcelOutput > PDFReaderVerification" FOLDER (LOOK FOR THE RECENT ONE)
=====================================================================================================================================

## IMPORTANT: BEFORE RUNNING THE TEST ENSURE THAT TEST DATA (Excel) FILE SHOULD BE CLOSED OTHERWISE TEST DATA FILE WILL BE CORRUPTED!!