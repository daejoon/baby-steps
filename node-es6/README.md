# Node-ES6

> [Browser ES6 Support](https://kangax.github.io/compat-table/es6/)를 살펴보면 Node >= 6.5 이상을 사용하면 97% 이상의 ES6를 지원한다.
> 하지만 아직도 Node에서는 import를 정식으로 지원하지 않아서 이 부분을 사용하려면 [Babel](https://babeljs.io/)을 이용해야 한다.
> 이 프로젝트는 ES6의 패키지 시스템을 사용하기 위한 프로젝트 이다.

## 구조
```
node-es6
├── .babelrc
├── .gitignore
├── README.md
├── dist
├── package-lock.json
├── package.json
├── src
│   ├── index.js
│   └── message.js
└── test
    └── index.spec.js


```
- .babelrc: babel 설정 파일
- dist: `babel`을 이용해서 ES5로 변환된 파일 들
- package.json: npm `package` 파일
- src: ES6로 만들어진 소스 파일
- test: ES6로 만들어진 테스트 파일

## 빌드
```
$ npm run build
```

## 테스트
```
$ npm run test
```

## 실행
```
$ npm run start
```

## 참고
- [My Node.js Setup (Mocha & Chai, Babel7, ES6)](https://dev.to/bnorbertjs/my-nodejs-setup-mocha--chai-babel7-es6-43ei)