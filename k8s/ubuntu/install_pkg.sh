#!/usr/bin/env bash

# root로 변경
sudo su -
echo "[INFO] install.sh, changed user: $(whoami)"

# docker 설치
apt-get update
apt-get install docker-ce=$2 docker-ce-cli=$2 containerd.io=$3 -y
systemctl enable --now docker

# mkdir /etc/docker
# cat <<EOF > /etc/docker/daemon.json
# {
# "exec-opts": ["native.cgroupdriver=systemd"],
# "log-driver": "json-file",
# "log-opts": {
# "max-size": "100m"
# },
# "storage-driver": "overlay2"
# }
# EOF
# systemctl daemon-reload
# systemctl restart docker

# preflight check error 보완
sed -i 's/"cri"//' /etc/containerd/config.toml
systemctl restart containerd
systemctl enable --now containerd

# kubernetes 설치
apt-get update
apt-get install kubelet=$1 kubectl=$1 kubeadm=$1 -y
systemctl enable --now kubelet
