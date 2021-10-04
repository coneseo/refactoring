###메서드 추출
어떤 코드를 그룹으로 묶어도 되겠다고 판단될 땐  
그 코드를 빼내어 목적을 잘 나타내는 직관적 이름의 메서드를 만들자.


~~~
    void printOwing(double amount){
        printBanner();
    
        // 세부 정보 출력
        System.out.println("name: "+ _name);
        System.out.println("amount: "+ amount);
    }
~~~

~~~
    void printOwing(double amount) {
        printBanner();
        printDetailis(amount);       
    }

    void printDetails(double amount) {
        System.out.println("name: " + _name);
        System.out.println("amount" + amount):
    }
~~~
동기  
메서드 추출 기법은 제일 많이 사용된다. 메서드가 너무 길거나 코드에 주석을 달아야만 의도를 이해할 수 있을 때,
나는 그 코드를 빼내어 별도의 메서드를 만든다. 

~~~
    void printOwing(){
        Enumeration e = _orders.elements();
        double outstanding = 0.0;
        System.out.println("*********")
        System.out.println("*****고객 외상****")
        System.out.println("*********")
    }

    while (e.hasMoreElements()){
        Order each = (Order) e.nextElements();
        outstanding += each.getAmount();
    }

    System.out.println("고객명: "+_name);
    System.out.println("외상액: "+outstanding);
~~~

~~~
    void printOwing(){
        Enumeration e = _orders.elements();
        double outstanding = 0.0;

        while (e.hasMoreElements()){
        Order each = (Order) e.nextElements();
        outstanding += each.getAmount();
        }
    }   
~~~
예제: 지역변수 사용  
지역변수 문제 중 제일 가벼운 경우는 지역변수가 읽히기만 하고 변경되지 않을 때다. 이럴 땐
지역변수를 그냥 매개변수로 전달하면 된다.

~~~
void printOwing() {
    Enumeration e = _orders.elements();
    double outstanding = 0.0;
    printBanner();
    
    while (e.hasMorElements()) {
        Order each = (Order) e.nextElement();
        outstanding += each.getAmount();
    }
    //세부 내역 출력
    System.out.println("고객명: " +_name)
    System.out.println("외상액: " + outstanding);
}
~~~

메서드 내용 직접 삽입  
메서드 기능이 너무 단순해서 메서드명만 봐도 너무 뻔할 땐
그 메서드의 긴ㅇ을 호출하는 메서드에 넣어버리고 그 메서는 삭제하자.

~~~
int getRating(){
    return (moreThanfiveLateDeliveries()) ? 2 : 1);

boolean moreThanfiveLateDeleiveries(){
    return _numberOfLateDeliveries > 5;
}
~~~
~~~
int getRating(){
    return (_numberOfLateDeliveries >5) ? 2 : 1;
}
~~~
임시변수 내용 직접 삽입  
```java
double basePrice = anOrder.basePrice();
return (basePrice > 1000)
```
  ⬇
```java
return (anOrder.basePrice() > 1000)
```
임시변수를 메서드 호출로 변환  
수식의 결과를 저장하는 임시변수가 있을 땐  
그 수식을 빼내어 메서드로 만든 후, 임시변수 참조부분을 전부 수식으로 교체하자.  
새로 만든 메서드는 다른 메서드에서도 호출 가능하다.  
```java
double basePrice = _quantity * _itemPrice;
if (basePrice > 1000)
    return basePrice * 0.95;
else
    return basePrice * 0.98;
```

```java
if (basePrice() > 1000)
    return basePrice() * 0.95;
else
    return basePrice() * 0.98;
...
double basePrice(){
    return _quantity * _itemPrice;
}
```
임시변수는 일시적이고 적용이 국소적 범위로 제한된다는 단점이 있다.  
임시변수는 자신이 속한 메서드이 안에서만 인식되므로, 그 임시변수에 접근하려다 보면 코드는
길어지기 마련이다.  
임시변수를 메서드 호출로 수정하면 클래스 안 모든 메서드가 그 정보에 접근할 수 있다.  
<br>
메서드 추출을 적용하기 전에 반드시 적용하는 것이 좋다.  지역 변수가 많을 수록 메서드 추출이
힘들어지므로 최대한 많은 변수를 메서드 호출로 고쳐야 한다.  

<br>
임시변수 분리  
루프 변수나 값 누적용 임시변수가 아닌 임시변수에 여러 번 값이 대입될 땐 각 대입마다 다른
임시변수를 사용하자.

```java
double temp = 2 * (_height + _width);
System.out.println(temp);
temp = _height * _width;
System.out.println(temp);
```  
```java
final double perimeter= 2 * (_height + _width);
System.out.println(perimeter);
final double area = _height * _width;
System.out.println(area);
```
임시변수는 값이 한 번만 대입되어이 한다. 값이 두 번 이상 대입된다는 건 그 변수가 메서드 안에서  
여러 용도로 사용된다는 반증이다. 여러 용도로 사용되는 변수는 각 용도 별로 다른 변수를 사용하게 분리해야 한다.  
임시변수를 하나를 두 가지 용도로 사용하면 코드를 분석하는 사람에게 혼동을 줄 수 있기 때문이다.  

```java
doube getDistanceTravelled (int time){
    double result;
    double acc = _primaryForce / _mass;
    int primaryTime = Math.min(time, _delay);
    result = 0.5 * acc * primaryTime * primaryTime;
    int secondaryTime = time - _delay;
    if (secondaryTime > 0){
        double primaryVel = acc * _delay;
        acc = (_primaryForce + _secondaryForce) / _mass;
        result += primaryVel * secondaryTime + 0.5 *
                 acc * secondaryTime * secondaryTime;
    }
    return result;
}
```
어색하다. acc값이 두 번 대입된다. 따라서 두가지 용도로 사용됨을 알 수 있다.  
하나는 첫 번째 받음 힘으로 생긴 초기 가속도를 저장하고, 다른 하나는 두 번째 힘으로 생긴
가속을 더한 값을 저장한다.  
이 변수는 각 용도에 맞게 둘로 나눠야 한다.  
우선 임시변수의 이름을 변경하고 그 새 변수를 final로 선언하자. 거기서부터 다음 대입문까지의  
임시변수 참조 부분을 전부 수정하자. 
```java
double getDistanceTravelled (int time){
    double result;
    final double primaryAcc = _primaryForce / _mass;
    int primaryTime = Math.min(time, _delay);
    result = 0.5 * primaryAcc * primaryTime * primaryTime;
    int secondaryTime = time - delay;
    
    if (secondaryTime > 0){
        double primaryVel = primaryAcc * _delay;
        double acc = (_primaryForce + _secondaryForce) / _mass;
        result += primaryVel * secondaryTime + 0.5 * acc * secondaryTime * secondaryTime;
    }
    return result;\
}
```
새 임시변수 이름을 첫 번째 용도(초기 가속도 저장)을 의미하도록 정했다.  
```java
doulbe getDistanceTravelled (int time){
    double result;
    final double primaryAcc = _primaryForce / _mass;
    int primaryTime = Math.min(time, _delay);
    result = 0.5 * primaryAcc * primaryTime * primaryTime;
    int secondaryTime = time - delay;
    
    if (secondarTime > 0){
    double primaryVle = primaryAcc * _delay;
    final double secondaryAcc = (_primaryForce + _secondaryForce) / _mass;
    result += primaryVel * secondaryTime + 0.5 * secondaryAcc * secondaryTime * secondaryTime;
    }
    return result;
    }
```
매개변수로의 값 대입 제거  
매개변수로의 값 대입이란 어떤 메서드에 foo 객체를 매개변수로 전달하면, '매개변수로의 값 대입'은  
foo의 값을 다른 객체 참조로 변경한다는 의미이다. 매개변수로 전달받은 객체에 어떠한 처리를 하든 상관없고 일상적인 작업이지만, foo의 값을 다른 객체 참조로 변경하는 것은 절대로 안된다. 
```java
void aMethod(Object foo){
    foo.modifySomeWay(); // 괜찮다.
    foo = anotherObject; // 고통과 절망을 안겨줄 것이다. 
}
```
전달받은 매개변수에 다른 객체 참조를 대입하면 코드의 명료성도 떨어지고, '값을 통한 전달'과 
'참조를 통한 전달'을 혼동하게 되기 때문이다. 자바는 '값을 통한 전달'만 사용하며, 지금 설명하는 것은 이를 바탕으로 한 것이다.  
'값을 통한 전달'을 사용하면 어떤한 매개변수 값 변화도 호출한 루틴에 반영되지 않는다. 
'참조를 통한 전달'을 사용해온 사람은 이것을 혼동할 수 있다.  
또한 메서드 안의 코드 자체도 혼동된다. 전달받은 객체를 나타내는 용도로만 매개변수를 사용하면 용도의 일관성으로 인해
코드가 훨씬 이해하기 쉬워진다.  
```java
int discount (int inputVal, int quantity, int yearToDate){
    if (inputVal > 50) inputVal -= 2;
    if (quantity > 100) inputVal -= 1;
    if (yearToDate > 10000) inputVal -= 4;
    return inputVal;
}
```
```java
int discount (int inputVal, int quantity, int yearToDate){
    int result = inputVal;
    if (inputVal > 50) result -= 2;
    if (quantity > 100) result -= 1;
    if (yearToDate > 10000) result -= 4;
    return inputVal;
}
```
이 관례를 강제 적용하고자 다음과 같이 final 키워드를 사용하자. 
```java
int discount (final int inputVal, final int quantity, final int yearToDate){
    int result = inputVal;
    if (inputVal > 50) result -= 2;
    if (quantity > 100) result -= 1;
    if (yearToDate > 10000) result -= 4;
    return inputVal;
}
```
저자는 final을 별로 쓰지않는다. 코드가 어려워 보이기 때문이다. 긴 메서드에는 다른 코드가 매개변수에 새 값을 
대입하는지 쉽게 파악하려고 final 키워드를 사용한다.  

자바에서 값을 통한 전달  
자바에서 값을 통한 전달 방식을 사용하면 코드가 뒤죽박죽된다. 다음 프로그램처럼 자바는 언제 어디서든 
반드시 값을 통한 전달만 사용한다.
```java
class Param{
    public static void main(String[] args){
        int x = 5;
        triple(x);
        System.out.println("triple 메서드 실행 후 x 값: "+ x);
    }
    private static void triple(int arg){
        arg = arg * 3;
        System.out.println("triple 메서드 안의 arg 값: "+arg);
    }   
}
```
메서드를 메서드 객체로 전환  
지역변수 때문에 메서드 추출을 적용할 수 없는 긴 메서드가 있을 땐, 
그 메서드 자체를 객체로 전환해서 모든 지역변수를 객체의 필드로 만들자.  
그런 다음 그 메서드를 객체 안의 여러 메서드르 쪼개면 된다.  
이 장에서 강조하는 핵심은 간결한 메서드의 아름다움 이다. 장황한 메서드에서 각 부분을 간결한 메서드로 빼내면 코드가 훨씬 이해하기 쉬워진다.  
메서드 분해를 어렵게 만드는 것은 지역변수다. 지역변수가 많으면 메서를 쪼개기 힘들 수 있다.  
임시변소를 메서드 호출로 전환을 적용하면 이런 어려움이 어느 정도 해소되지만, 분해가 필요한 메서드를 분해할 수 없을 때도 있다.  
이럴 땐 메서드 객체러 수정해야 한다.  
메서드를 메서드 객체로 전환 기법을 사용하면 모든 지역변수가 메서드 객체의 속성이 된다.  
그러면 그 객체에 메서드 추출을 적용해서 원래의 메서드를 쪼개어 여러 개의 추가 메서드를 만들면 된다.  

알고리즘 전환  
알고리즘을 더 분명한 것으로 교체해야 할 땐 해당 메서드의 내용을 새 알고리즘으로 바꾸자.  
```java
String foundPerson(String[] people){
    for (int i = 0; i < people.length; i++){
        if (people[i].equals("Don")){
            return "Don";
        }
        if (people[i].equals("John")){
            return "John";
        }
        if (people[i].equals("Kent")){
            return "Kent";
        }
    }
    return "";
}
```
```java
    String foundPerson(String[] people){
        List candidates = Arrays.asList(new String[] {"Don", "John", "Kent"});
        for (int i=0; i<people.length; i++){
            if (candidates.contains(people[i]))
                return people[i];
        }
        return "";   
    }
```
어떤 목적을 달성하는 방법은 여러 가지가 있기 마련이다. 다만, 다른 방법에 비해 쉬운 방법이 있을 뿐이다.  
알고리즘도 마찬가지다. 어떤 기능을 수행하기 위한 비교적 간단한 방법이 있다면 복잡한 방법을 좀 더 간단한 방법으로 교체해야한다.  
리펙토링은 복잡한 코드를 간단한 부분으로 쪼갤 수 있지만, 간혹 알고리즘을 전부 삭제하고 더 간단한 알고리즘으로 교체해야 하는 상황에 부딪힐 때도 있다.  
문제에 관해 점점 알아가다가 그보다 더 간단한 방법이 있음을 알게 됐을 때가 바로 그 상황이다.  
어떤 작업을 약간 다르게 처리해야 해서 알고리즘을 변경해야 할 때도 있는데, 이럴 때는 좀 더 변경하기 쉬운 알고리즘으로 
교체하는 것이 간편하다. 이렇게 하려면 먼제 메서드를 잘게 쪼개야 한다. 
