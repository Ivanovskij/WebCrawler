[![Java CI with Maven](https://github.com/Ivanovskij/WebCrawler/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/Ivanovskij/WebCrawler/actions)
[![Code Coverage](https://codecov.io/gh/Ivanovskij/WebCrawler/branch/master/graph/badge.svg)](https://codecov.io/gh/Ivanovskij/WebCrawler?branch=master)
# Open source WebCrawler
Simple web crawler based on Java 11 open JDK.

# Overview
- [Features/Supports](https://github.com/Ivanovskij/WebCrawler/tree/master#featuressupports)
- [Installation](https://github.com/Ivanovskij/WebCrawler/tree/master#installation)
- [Development](https://github.com/Ivanovskij/WebCrawler/tree/master#development)

# Features/Supports
  - Deep search
  - Max visited pages
  - Sync/Async worker
  - Terms search sorting by total hints
  - Exports statistics to csv file or to separate csv files

# Installation
1. Download latest realise files.
2. Check crawl settings in crawlsearcher.txt.
3. Run WebCrawler.jar
4. When crawler finishes its work, you will see reports in the csv files if you included -csv flag.
*******
crawlsearcher.txt allows you to change:<br><br>
`- root seed`<br>
`- depth`<br>
`- max visited pages`<br>
`- sync/async worker strategy`<br>
`- st or et params mean start terms & end terms, so these params run term searcher`<br>
`- csv export statistic to csv files`<br>

# Development
Want to contribute? Great!
1. Fork the project & clone locally.
2. Create an upstream remote and sync your local copy before you branch.
3. Branch for each separate piece of work.
4. Do the work, write good commit messages.
5. Push to your origin repository.
6. Create a new PR in GitHub.
7. Respond me to code review feedback.

If you want to contribute to an open source project, the best one to pick is one that you are using yourself. The maintainers will appreciate it!