
if [ -z $1 ]; then
  echo "Usage: $0 <unit_test>"
  exit
fi

hamcrest_core="path/to/hamcrest-core-1.3.jar"
junit="path/to/junit-4.12.jar"

echo "javac -cp .:$junit:$hamcrest_core *.java"
javac -cp .:$junit:$hamcrest_core *.java

echo "java -cp .:$junit:$hamcrest_core org.junit.runner.JUnitCore $1"
java -cp .:$junit:$hamcrest_core org.junit.runner.JUnitCore $1
