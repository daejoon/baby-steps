# Kubernetes 구축하기

인프런 강좌 두곳을 보면서 공부하는곳 둘다 Centos7에 Virtual Box 에서 진행하는데 M1 이어서 Ubuntu와 Parallels로 구성

## 강좌

* [대세는 쿠버네티스](https://www.inflearn.com/course/%EC%BF%A0%EB%B2%84%EB%84%A4%ED%8B%B0%EC%8A%A4-%EA%B8%B0%EC%B4%88/dashboard)
* [쿠버네티스 쉽게시작하기](https://www.inflearn.com/course/%EC%BF%A0%EB%B2%84%EB%84%A4%ED%8B%B0%EC%8A%A4-%EC%89%BD%EA%B2%8C%EC%8B%9C%EC%9E%91/dashboard)

## 환경구성

* Macbook Pro 16 M1
* Parallels Desktop Pro
* Ubuntu 20.04 LTS arm64

## Parallels Provider 설치

* [How to use Vagrant with Parallels Desktop](https://kb.parallels.com/en/122843)

### Plugin 설치

```
$ vagrant plugin install vagrant-parallels
```

### Plugin 업그레이드 

```
$ vagrant plugin update vagrant-parallels
```

## Vagrant 명령어

| 명령어             | 설명           |
|:----------------|:-------------|
| vagrant up      | 가상머신 기동      |
| vagrant status  | 가상머신 상태 확인   |
| vagrant ssh     | 가상머신에 접속     |
| vagrant halt    | 가상머신 정지      |
| vagrant suspend | 가상머신 휴면      |
| vagrant resume  | 가상머신 휴면에서 복원 |
| vagrant reload  | 가상머신 재시동     |
| vagrant destroy | 가상머신 제거      | 

## 참고

* [Vagrant Box - Ubuntu-20.04](https://app.vagrantup.com/bento/boxes/ubuntu-20.04)
* [Vagrant Parallels Docs](http://parallels.github.io/vagrant-parallels/docs/)
* [Vagrant Parallels Github](https://github.com/Parallels/vagrant-parallels)
* [Vagrant 명령어 참고](https://junistory.blogspot.com/2017/08/virtualbox-vagrant.html)
