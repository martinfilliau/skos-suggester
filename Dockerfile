FROM makuk66/docker-solr
MAINTAINER Micah Martin <micahlmartin@gmail.com>

ADD ./src/main/resources/solr/schema.xml /opt/solr/example/solr/collection1/conf/schema.xml
ADD ./src/main/resources/solr/solrconfig.xml /opt/solr/example/solr/collection1/conf/solrconfig.xml

EXPOSE 8983
CMD ["/bin/bash", "-c", "cd /opt/solr/example; java -jar start.jar"]

