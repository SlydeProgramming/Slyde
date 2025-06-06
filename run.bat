call ./gradlew build
call java -jar .\app\build\libs\app-all.jar test.sly
call clang ./out.ll
call ./a