# spring-shopping-precourse

## 사용자 스토리

- 사용자는 상품을 (상점에) 추가할 수 있다.
- 사용자는 (상정의) 상품을 수정할 수 있다.
- 상품은 잘못된 이름을 가져서는 안 된다.
    - 상품은 최대 15자를 넘는 이름을 가져서는 안 된다.
    - 상품은 일부 특수 문자를 허용하지 않는다.
    - 상품은 일부 특수 문자는 허용한다.
    - 상품은 비속어를 포함하지 않는다.

---

- 상품은 최대 15자를 넘는 이름을 가져서는 안 된다.
    - [x] 동해물과백두산이마르고닳도록
    - [ ] 동해물과 백두산이 마르고 닳도록

```gherkin
Given 상품 이름이 "동해물과 백두산이 마르고 닳도록"일 때
When 상품을 생성하면
Then 400 Bad Request를 응답한다.
AND "상품의 이름은 15자를 넘길 수 있습니다."라고 응답한다.
```

```gherkin
Given 기존 상품이 존재할 때
And 변경하고자 하는 상품 이름이 "동해물과 백두산이 마르고 닳도록"
When 상품을 수정하면
Then 400 Bad Request를 응답한다
And "상품의 이름은 15자를 넘길 수 없습니다."라고 응답한다.
```

상품은 일부 특수 문자는 허용하지 않는다.

- [ ] (할인) 아메리카노
- [ ] 아메리카노 *할인
- [ ] ...

# 용어 사전

| 한글명     | 영문명              | 설명                                                    |
|---------|------------------|-------------------------------------------------------|
| 판매자     | Seller           | 상품을 관리(등록, 수정, 삭제)하며 판매하는 주체                          |
| 구매자     | Buyer            | 상품을 구매하는 주체이며 위시 리스트를 추가, 삭제하는 주체                     |
| 관리자     | Admin            | 쇼핑시스템을 전반적으로 관리할 수 있는 권한을 가진 주체                       |
| 비속어     | Slang            | PurgoMalum에서 비속어로 규정한 단어                              |
| 토 큰     | Token            | 인증인가된 사용자를 식별 할 수 있는 값                                |
| 이벤트     | Event            | 한명 또는 복수의 Actor에게 영향을 끼치는 사건                          |
| 위시리스트   | WishList         | 구매자가 자신의 마음에 드는 삼품을 저장해서 만드는 리스트, 이 리스트를 사용해서 구매는 불가능 |
| 비밀번호 찾기 | Find my password | 판매자 혹은 구매자가 비밀번호를 잊어버렸거나 재발급을 원할 때 재발급 하는 기능          |
| 클라이언트   | Client           | 쇼핑 시스템을 이용하는 모든 사람                                    |

# 모델링

+ `Product`는 중복되지 않는 `Id`를 가진다.
+ `Product`는 `Name`을 가진다.
+ `Product`는 `Price`를 가진다.
+ `Product`는 `ImageUrl`를 가진다.
+ `Name`은 공백을 포함한 최대 15자 길이의 이름을 가진다.
+ `Name`은 다음 특수문자를 허용한다. ( ), [ ], +, -, &, /, _
+ `Name`은 비속어를 포함할 수 없다.
+ `SlangFilterClient`는 문자열에 비속어가 포함되어 있는지를 확인한다.
+ `Price`는 유리수이다.
+ `ImageUrl`은 `URI`을 가진다.
+ `ImageUrl`은 .png, .jpg 확장자여야 한다.
+ `User`은 `Role`을 가진다.
+ `User`은 `Email`을 가진다.
+ `User`은 `Password`을 가진다.
+ `Role`은 `Seller`, `Buyer`, `Admin`을 값으로 가진다.
+ `Wishlist`는 중복되지 않는 `Id`를 가진다.
+ `Wishlist`는 `User`, `Product`을 가진다.
+ `Role`이 `Seller`인 `User`는 `Product`를 생성한다.
+ `Role`이 `Seller`인 `User`는 `Product`를 수정한다.
+ `Role`이 `Seller`인 `User`는 `Product`를 삭제한다.
+ `Role`이 `Buyer`인 `User`는 `Wishlist`를 생성한다.
+ `Role`이 `Buyer`인 `User`는 `Wishlist`를 삭제한다.
+ `TokenGenerator`는 `Token`을 생성한다.
+ `HashStatistics`는 문자열을 단방향 암호화 한다.
+ `HashStatistics`는 원본 문자열과 암호화된 문자열이 일치하는지 확인한다.
+ `EncryptStatistics`는 원본 문자열을 암호화 한다.
+ `EncryptStatistics`는 암호화된 문자열을 복호화 한다.

## 이벤트

## 정책
```mermaid
flowchart LR
    A[회원] -->|이메일 형식이 아닌 문자 입력| A[회원]
    A[회원] -->|대소문자,숫자,특수문자 하나씩 포함된 8~15자가 아닌 비밀번호 입력| A[회원]
    A[회원] -->|이메일 입력| B[중복 체크]
    B[중복 체크] -->|신규 이메일| C[중복확인]
    C[중복확인] -->|신규 이메일| D[회원가입]
    A[회원] -->|대소문자,숫자,특수문자 하나씩 포함된 8~15자 비밀번호 입력| D[회원가입]
```

```mermaid

flowchart TD
    A[Start] --> B{Is it?}
    B -- Yes --> C[OK]
    C --> D[Rethink]
    D --> B
    B -- No ----> E[End]
```

```mermaid
flowchart LR
    A[Hard] -->|Text| B(Round)
    B --> C{Decision}
    C -->|One| D[Result 1]
    C -->|Two| E[Result 2]
```

```mermaid
flowchart TB
    c1-->a2
    subgraph one
    a1-->a2
    end
    subgraph two
    b1-->b2
    end
    subgraph three
    c1-->c2
    end
```


```mermaid
flowchart TD
    subgraph 등록
        정책1(등록 정책) --> 정책2(수강생 정책)
        정책1 --> 정책3(수강 대기자 등록 정책)
        정책2 --> 상태1(수강생)
        정책3 --> 상태2(수강 대기자 정책)
    end
    
    사용자1(사용자1) --> |수강 신청| 정책1
    수강대기자1(수강 대기자1) --> |수강 신청| 정책3
    
    정책2 --> |수강 승인| 승인수강생(수강 승인된 수강생)
    정책2 --> |수강 미승인| 미승인수강생(수강 승인되지 않은 수강생)
    정책3 --> 수강대기자(수강 대기자)
    
    사용자2(사용자2) -.-> 등록
```

```mermaid
flowchart TD
    classDef actorClass fill:#f9f,stroke:#333,stroke-width:2px,style:dotted;

    사용자1[사용자1]:::actorClass --> |수강 신청| 등록정책(등록 정책)
    사용자2[수강 대기자1]:::actorClass --> |수강 신청| 수강대기자정책(수강 대기자 등록 정책)
    
    등록정책 --> 수강생정책(수강생 정책)
    등록정책 --> 수강대기자정책
    
    수강생정책 --> 수강생(수강생)
    수강대기자정책 --> 수강대기자(수강 대기자)
    
    수강생정책 --> |수강 승인| 승인수강생(수강 승인된 수강생)
    수강생정책 --> |수강 미승인| 미승인수강생(수강 승인되지 않은 수강생)
    수강대기자정책 --> 대기자(수강 대기자)
    
    사용자3[사용자2]:::actorClass -.-> 등록정책
```

```mermaid
    C4Context
      title System Context diagram for Internet Banking System
      Enterprise_Boundary(b0, "BankBoundary0") {
        Person(customerA, "Banking Customer A", "A customer of the bank, with personal bank accounts.")
        Person(customerB, "Banking Customer B")
        Person_Ext(customerC, "Banking Customer C", "desc")

        Person(customerD, "Banking Customer D", "A customer of the bank, <br/> with personal bank accounts.")

        System(SystemAA, "Internet Banking System", "Allows customers to view information about their bank accounts, and make payments.")

        Enterprise_Boundary(b1, "BankBoundary") {

          SystemDb_Ext(SystemE, "Mainframe Banking System", "Stores all of the core banking information about customers, accounts, transactions, etc.")

          System_Boundary(b2, "BankBoundary2") {
            System(SystemA, "Banking System A")
            System(SystemB, "Banking System B", "A system of the bank, with personal bank accounts. next line.")
          }

          System_Ext(SystemC, "E-mail system", "The internal Microsoft Exchange e-mail system.")
          SystemDb(SystemD, "Banking System D Database", "A system of the bank, with personal bank accounts.")

          Boundary(b3, "BankBoundary3", "boundary") {
            SystemQueue(SystemF, "Banking System F Queue", "A system of the bank.")
            SystemQueue_Ext(SystemG, "Banking System G Queue", "A system of the bank, with personal bank accounts.")
          }
        }
      }

      BiRel(customerA, SystemAA, "Uses")
      BiRel(SystemAA, SystemE, "Uses")
      Rel(SystemAA, SystemC, "Sends e-mails", "SMTP")
      Rel(SystemC, customerA, "Sends e-mails to")

      UpdateElementStyle(customerA, $fontColor="red", $bgColor="grey", $borderColor="red")
      UpdateRelStyle(customerA, SystemAA, $textColor="blue", $lineColor="blue", $offsetX="5")
      UpdateRelStyle(SystemAA, SystemE, $textColor="blue", $lineColor="blue", $offsetY="-10")
      UpdateRelStyle(SystemAA, SystemC, $textColor="blue", $lineColor="blue", $offsetY="-40", $offsetX="-50")
      UpdateRelStyle(SystemC, customerA, $textColor="red", $lineColor="red", $offsetX="-50", $offsetY="20")

      UpdateLayoutConfig($c4ShapeInRow="3", $c4BoundaryInRow="1")



```