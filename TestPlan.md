# Test Plan

Automated testing with __JUnit__ would be nice, but in the meantime ...

## Keywords

Verify non-existent 'Keywords' file aborts correctly:

1) Rename `Keywords` file to `KeywordsAA`.

2) Run the application:

	```
	$ make run
	java -cp src/ Driver
	Unable to open file 'Keywords'
	Could not parse 'Keywords' file, aborting ...
	Makefile:13: recipe for target 'run' failed
	make: *** [run] Error 255
	$
	```

3) Rename `KeywordsAA` file back to `Keywords`.

## PostScript

Verify non-existent 'PostScript' file aborts correctly:

1) Rename `PostScript` file to `PostScriptAA`.

2) Run the application:

	```
	$ make run
	java -cp src/ Driver
	Unable to open file 'PostScript'
	Could not parse 'PostScript' file, aborting ...
	Makefile:13: recipe for target 'run' failed
	make: *** [run] Error 255
	$
	```

3) Rename `PostScriptAA` file back to `PostScript`.

## Emails

Verify empty 'Emails' directory aborts correctly:

1) Rename `Emails` directory to `EmailsAA`.

2) Run the application:

	```
    $ make run
    java -cp src/ Driver
    
    Keywords to be scanned for: [Facebook, Fakebook, Glados, Trump, Twitter, re-tweet, re-tweeted, re-tweets, retweet, retweeted, retweets, tweet, tweeted, tweeting, tweets]
    
    Directory 'Emails' has no emails, returning 'null'
    Could not parse any emails in the 'Emails' directory, aborting ...
    Makefile:13: recipe for target 'run' failed
    make: *** [run] Error 255
    $
	```

3) Rename `EmailsAA` directory back to `Emails`.
