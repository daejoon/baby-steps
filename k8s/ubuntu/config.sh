#!/usr/bin/env bash

# root로 변경
sudo su -
echo "[INFO] config.sh, changed user: $(whoami)"

# 방화벽 해제
systemctl stop ufw
systemctl disable ufw

# swap 비활성화
swapoff -a
sed -i '/swap/s/^/#/' /etc/fstab

# 필수 패키지 다운로드
apt-get update
apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release

# docker repo 추가
mkdir -p /etc/apt/keyrings
rm -rf /etc/apt/keyrings/docker-archive-keyring.gpg
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker-archive-keyring.gpg
echo \
  "deb [signed-by=/etc/apt/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list >/dev/null

# 쿠버네티스 repo 추가
rm -rf /etc/apt/keyrings/kubernetes-archive-keyring.gpg
curl -fsSLo /usr/share/keyrings/kubernetes-archive-keyring.gpg https://packages.cloud.google.com/apt/doc/apt-key.gpg
echo "deb [signed-by=/usr/share/keyrings/kubernetes-archive-keyring.gpg] https://apt.kubernetes.io/ kubernetes-xenial main" |
  sudo tee /etc/apt/sources.list.d/kubernetes.list

# br_netfilter 모듈 로드
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
br_netfilter
EOF
modprobe br_netfilter

# iptables 커널 조정
cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF

# Host 등록
sed -i -r '/^192\.168\.100\.(.+)node[0-9]?$/d' /etc/hosts
echo "192.168.100.10 master-k8s" >>/etc/hosts
for ((i = 0; i < $1; i++)); do echo "192.168.100.10$i worker$i-k8s" >>/etc/hosts; done

# config DNS
# cat <<EOF > /etc/resolv.conf
# nameserver 1.1.1.1 #cloudflare DNS
# nameserver 8.8.8.8 #Google DNS
# EOF
