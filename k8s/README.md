# Kubernetes 구축하기

인프런 강좌 두곳을 보면서 공부하는데 둘다 Centos7에 Virtual Box 에서 진행하는데 M1 이어서 Ubuntu와 Parallels로 구성

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

| 명령어             | 설명                                          |
|:----------------|:--------------------------------------------|
| vagrant up      | 가상머신 기동                                     |
| vagrant status  | 가상머신 상태 확인                                  |
| vagrant ssh     | 가상머신에 접속                                    |
| vagrant halt    | 가상머신 정지                                     |
| vagrant suspend | 가상머신 휴면                                     |
| vagrant resume  | 가상머신 휴면에서 복원                                |
| vagrant reload  | 가상머신 재시동 (보통 Vagrantfile을 수정하고 다시 적용하는데 사용) |
| vagrant destroy | 가상머신 제거                                     | 

* Multi VM에서 SSH로 접속

```shell
$ vagrant ssh {machineName}
```

## 심볼릭 링크

```shell
$ ln -s /data/_Lecture_k8s_learning.kit _Lecture_k8s_learning.kit
$ ln -s /data/kube_follow kube_follow
```

## apt 명령어

| 명령어                  | 설명             |
|:---------------------|:---------------|
| apt-get update       | 패키지 인덱스 업데이트   |
| apt-get upgrade      | 패키지 업그레이드      |
| apt-get install      | 패키지 설치         |
| apt-get remove       | 패키지 제거         |
| apt list             | 패키지 리스트        |
| apt list --installed | 설치된 패키지 리스트    |
| apt-cache show       | 패키지 정보         |
| apt-cache search     | 패키지 검색         |
| apt-cache policy     | 패키지 정책 (버전 목록) | 

* [apt 명령어](https://blog.outsider.ne.kr/346)

## Troubleshooting

* 처음 신규 설치가 아닌 `vagrant destroy -f`로 삭제 후 `vagrant up`으로 다시 설치시 오류가 발생한다면 `.Vagrant`폴더를 삭제하고 진행해 보자

* `vagrant up --no-parallel`로 실행

## 참고

* [Vagrant Box - Ubuntu-20.04](https://app.vagrantup.com/bento/boxes/ubuntu-20.04)
* [Vagrant Parallels Docs](http://parallels.github.io/vagrant-parallels/docs/)
* [Vagrant Parallels Github](https://github.com/Parallels/vagrant-parallels)
* [Vagrant 명령어 참고](https://junistory.blogspot.com/2017/08/virtualbox-vagrant.html)
