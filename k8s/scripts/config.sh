#!/usr/bin/env bash

# 실행 유저 확인
echo "[INFO] config.sh, current user: $(whoami)"

# 타임존 변경
echo "[INFO] set-timezone Asia/Seoul "
timedatectl set-timezone Asia/Seoul

# vim configuration
echo 'alias vi=vim' >>/etc/profile

# swap 비활성화
swapoff -a
sed -i -r 's/(.+ swap .+)/#\1/' /etc/fstab

# docker, kubernetes 레파지토리 다운로드를 위한 유틸리티 패키지 설치
rm -rfv /var/lib/apt/lists/*
apt-get update
apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release

# docker repo 추가
mkdir -p /etc/apt/keyrings
rm -rfv /etc/apt/keyrings/docker-archive-keyring.gpg
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker-archive-keyring.gpg
echo \
  "deb [signed-by=/etc/apt/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list >/dev/null

# 쿠버네티스 repo 추가
rm -rfv /etc/apt/keyrings/kubernetes-archive-keyring.gpg
curl -fsSLo /usr/share/keyrings/kubernetes-archive-keyring.gpg https://packages.cloud.google.com/apt/doc/apt-key.gpg
echo "deb [signed-by=/usr/share/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-xenial main" |
  sudo tee /etc/apt/sources.list.d/kubernetes.list

# 방화벽 해제
systemctl stop ufw
systemctl disable ufw

# br_netfilter 모듈 로드
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
overlay
br_netfilter
EOF
modprobe overlay
modprobe br_netfilter

# 필요한 sysctl 파라미터를 설정하면, 재부팅 후에도 값이 유지된다.
cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-iptables  = 1
net.bridge.bridge-nf-call-ip6tables = 1
net.ipv4.ip_forward                 = 1
EOF

# 재부팅하지 않고 sysctl 파라미터 적용하기
sysctl --system

# Host 등록
sed -i -r '/^192\.168\.10\.(.+)node$/d' /etc/hosts
echo "192.168.10.10 master-node" >>/etc/hosts
for ((i = 1; i <= $1; i++)); do echo "192.168.10.10$i worker$i-node" >>/etc/hosts; done

# config DNS
cat <<EOF >/etc/resolv.conf
nameserver 1.1.1.1 #cloudflare DNS
nameserver 8.8.8.8 #Google DNS
EOF
