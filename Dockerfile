FROM vdvguillaume/testquarkus

USER root

RUN yum install -y dos2unix

WORKDIR /work/quarkus-app

CMD ["bash"]
