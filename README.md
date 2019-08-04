# Udacity-Project-Baking-App
Udacity's Android Developer Nanodegree: Baking App Project

Youtube [link](https://youtu.be/LD_s1KTq_wo) for this project (동영상 데모 링크)

(한글 README는 아래에 있습니다.)

## Project Overview

Your task is to create an Android Baking App that will allow Udacity’s resident baker-in-chief, Miriam, to share her recipes with the world. You will create an app that will allow a user to select a recipe and see video-guided steps for how to complete it.
The recipe listing is located [here](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json).
The JSON file contains the recipes' instructions, ingredients, videos and images you will need to complete this project. Don’t assume that all steps of the recipe have a video. Some may have a video, an image, or no visual media at all.
One of the skills you will demonstrate in this project is how to handle unexpected input in your data -- professional developers often cannot expect polished JSON data when building an app.

* [Sample Mock Link](https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58dee986_bakingapp-mocks/bakingapp-mocks.pdf)



## Main Features

- Use MediaPlayer/Exoplayer to display videos.
- Handle error cases in Android.
- Add a widget to your app experience.
- Leverage a third-party library in your app.
- Use Fragments to create a responsive design that works on phones and tablets.


## Project Requirements

### Common Project Requirements
- App is written solely in the Java Programming Language.
- Submission must use stable release versions of all libraries, Gradle, and Android Studio. Debug/beta/canary versions are not acceptable.
- App conforms to common standards found in the Android Nanodegree General Project Guidelines. [link](http://udacity.github.io/android-nanodegree-guidelines/core.html)

### General App Usage
- Display recipes: App should display recipes from provided network resource.
- App Navigation: App should allow navigation between individual recipes and recipe steps.
- Utilization of RecyclerView: App uses RecyclerView and can handle recipe steps that include videos or images.

### Components and Libraries
- Master Detail Flow and Fragments: Application uses Master Detail Flow to display recipe steps and navigation between them.
- Exoplayer(MediaPlayer) to display videos: Application uses Exoplayer to display videos.
- Proper utilization of video assets: Application properly initializes and releases video assets when appropriate.
- Proper network asset utilization: Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
- UI Testing: Application makes use of Espresso to test aspects of the UI.
- Third-party libraries: Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with ContentProviders if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.

### Homescreen Widget
- Application has a companion homescreen widget.
- Widget displays ingredient list for desired recipe.





# 프로젝트 개요

해당 프로젝트는 사용자가 빵 종류를 선택하면 해당 빵의 조리법을 순서대로 보여주는 안드로이드 어플리케이션을 만드는 게 목표임. 조리법은 비디오 기반의 가이드로, 다음 [링크](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json)를 통해 받음.
네트워크요청을 통해 받은 JSON 파일은 앱을 구현하기 위한 레시피 가이드, 필요재료, 비디오 및 이미지에 관한 정보를 담고 있음. 조리 단계마다 이미지, 비디오 등의 정보들을 제각각 가지고 있거나 정보가 없는 경우도 있기 때문에 이 부분을 예외처리 해야 함. 이를 통해 통신으로 받아온 데이터가 불완전할 경우에 대한 예외처리를 배울 수 있음.

제공받은 샘플 목업 [링크](https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58dee986_bakingapp-mocks/bakingapp-mocks.pdf)


## 구현 시 주안점
- 레시피(요리법)를 보여주기 위하여 MediaPlayer/Exoplayer를 사용
- 에러 케이스 핸들링
- 앱 위젯 구현
- 서드파티 라이브러리를 사용
- 스마트폰 및 태블릿 대응을 위하여 프래그먼트 사용


## 프로젝트 요구사항

### 공통
- 자바 언어를 사용하여 작성
- 안정된 버전의 라이브러리, 그레이들, 안드로이드 스튜디오를 사용해야 함
- 유다시티의 안드로이드 나노디그리의 프로젝트 [가이드라인](http://udacity.github.io/android-nanodegree-guidelines/core.html)을 준수

### 기본 유저 인터페이스
- 레시피 출력: 제공된 제이슨 [데이터](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json)를 받아와 레시피를 출력함
- 화면간 이동: 요리법 내의 스텝(단계)를 이동할 수 있어야 함
- 리사이클러뷰 사용: 리스트를 보여줄 때는 리사이클러 뷰를 사용하고, 레시피 내 단계는 비디오 혹은 이미지를 보여주어야 함

### 컴포넌트 및 라이브러리
- 마스터-디테일 플로우 및 프레그먼트: 레시피와 스텝을 보여주고, 스텝 내 이동을 위하여 앱은 마스터-디테일 플로우를 사용해야 함
- 엑소플레이어(미디어플레이어): 앱은 비디오를 보여주기 위하여 엑소플레이어를 사용함
- 비디오 에셋의 활용: 앱은 비디오 초기화 및 릴리즈를 적절히 수행해야 함
- 네트워크 요청: 앱은 네트워크 요청으로부터 받아오는 데이터를 가지고 미디어 에셋을 추출해야 함
- UI테스팅: UI 테스트를 위해 에스프레소를 사용해야 함
- 서드파티 라이브러리 사용: 앱의 기능 향상을 위해 서드파티 라이브러리를 활용해야 함

### 위젯
- 레시피에 해당하는 재료를 보여주는 위젯을 구현해야 함
