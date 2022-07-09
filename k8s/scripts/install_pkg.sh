#!/usr/bin/env bash

# root로 변경
sudo su -
echo "[INFO] install.sh, changed user: $(whoami)"

# docker 설치
apt-get update
apt-get install -y docker-ce docker-ce-cli containerd.io
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

# kubernetes 설치
apt-get update
apt-get install -y kubelet=$1 kubectl=$1 kubeadm=$1
systemctl enable --now kubelet