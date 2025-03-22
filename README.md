

### Download Instructions

Go to relesess and download the latest jar then you can compile your .sly code into .ll
This requires java version 21
Download Link:
https://www.oracle.com/java/technologies/downloads/#java21

Also install the Slyde Language extention on vs code!

once you have java downloaded you can run


```
java -jar %SLYDE_FILE_PATH% %PATH_TO_SLY% %OUTPUT_FILE_PATH%
```

 example

```
java -jar ./Slyde.jar Hello.sly o.ll
```

### Documentation


Suported Types:
```
boolean, int, void
```

Booleans values:

```
false | true | off | on | no | yes
```


Class declaration & Field Delceration:


example:
```
class Foo{
  int hello = 0;
}
```

constructors:

```
class Foo{
  int hello = 0;

  constructor(){
    //body
  }
}
```

Main Method for startup ONLY ONE PER FILE:

```
class Foo{
  int hello = 0;
}

main(){
   //body
}
```

Method decleration:

```
class Foo{
  int hello = 0;

  void hi(){
    //body;
  }

}
```

IF Statments:

```
class Foo{
  boolean isBoolean = yes;

  Main(){
    if(isBoolean){
      //true branch
    } else {
      //false branch
    }
  }
  
}

```





