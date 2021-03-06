## sparkling-reviews

An application to analyse the user reviews about a product to find the sentiments and to extract some particular 
attributes (key words) so as to define some base for the user construe about the product.

**Currently facing low-performance issues (EXECUTION IS NOT COMPLETING). Number of issues like GC overhead exceeded, and java heap space out of memory.**

### Dependencies

1. Java 8 - v1.8 or above
2. Maven - v3.5.3 or above (lower versions might work haven't tested)

#### Dataset

For sample use, original data of 14 years from the [dataset](http://jmcauley.ucsd.edu/data/amazon/) is filtered to 
only 1 year of data (of 2014 only) with few columns renamed. The filtered data can be downloaded from [here](https://drive.google.com/open?id=1wy49uyiPYpQkVlYtOR8OlXSx9va2EP5s).
To execute on a local machine even smaller dataset of only two products can be downloaded from [here](https://drive.google.com/open?id=1My2TNEnLTfkLFBIrwa8vSf1T4ieKeWlG).

### Building

1. Build tool used is [Apache Maven](http://maven.apache.org/).
2. Go to the project directory `cd ${project_directory}`.
3. Execute command `mvn clean install`.
4. A folder named `target` will be created. Inside that the application jar named `sparkling-reviews-0.1.jar` will be created.

### Execution

1. Pick up the jar file `sparkling-reviews-0.1.jar`, and place it in the favourable location 
(from where you can execute `apache spark` commands).
2. Spark command structure to execute the application:-

        
        spark-submit --class sparkling.reviews.core.Trigger \
        --master ${master_you_want} \
        --conf ${spark conf 1} \
        --conf ${spark conf 2} \
        --conf ${spark conf 3} \
        --queue ${if using yarn} \
        sparkling-reviews-0.1.jar \
        ${input_data_path}
        ${path_to_store_the_results}
        

### License

[Apache License - Version 2.0](https://github.com/Pratik-Barhate/sparkling-reviews/blob/master/LICENSE)

### Credits

* Thanks to [John Snow Labs](https://github.com/JohnSnowLabs) for the development of open source (and free)
[spark-nlp](https://github.com/JohnSnowLabs/spark-nlp) library.

* Amazon review [dataset](http://jmcauley.ucsd.edu/data/amazon/) managed by [Julian McAuley](http://cseweb.ucsd.edu/~jmcauley/) 
was used while development. Thank you for providing the data.
