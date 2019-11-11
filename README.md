# web-1t-5grams
Easy way to access Google Web 1T 5-Grams

## Requirements
* Oracle JDK 8+ 
* Maven 4.0.0
* zcat & grep utilities

## Usage
### Create an index from files in the directory
```java
final String pathToNgrams = "path/to/1grams";
final String pathToIndex = "path/to/1grams.index";
IndexFactory.createIndex(pathToNgrams, pathToIndex);
```

### Create an index from the defined list of files
```java
final String pathToIndex = "path/to/bi-grams.index";
final List<String> fileNames = Arrays.asList(
      "path/to/2grams/bi-grams-1.tsv.gz",
      "path/to/2grams/bi-grams-2.tsv.gz"
);
IndexFactory.createIndex(fileNames, pathToIndex);
```

### Get a score 
```java
final Map<Integer, String> indexes = new HashMap<>();
indexes.put(1, "path/to/uni-grams.index");
indexes.put(2, "path/to/bi-grams.index");

final Searcher searcher = new Searcher(indexes);
searcher.getScore(2, "show me");
searcher.getScore(1, "score");
```
