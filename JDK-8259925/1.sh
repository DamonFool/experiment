TEST="Bar"

${JDK}/bin/javac \
   --add-modules=jdk.incubator.vector \
   ${TEST}.java

${JDK}/bin/java \
   -Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=2 \
   -Xbatch \
   -XX:-TieredCompilation \
   -XX:+PrintCompilation -XX:+PrintOptoAssembly \
   --add-modules=jdk.incubator.vector \
   ${TEST}  | tee 2.log

${JDK}/bin/java \
   -Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=3 \
   -Xbatch \
   -XX:-TieredCompilation \
   -XX:+PrintCompilation -XX:+PrintOptoAssembly \
   --add-modules=jdk.incubator.vector \
   ${TEST}  | tee 3.log
