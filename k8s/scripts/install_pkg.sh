#!/usr/bin/env bash

# 실행 유저 확인
echo "[INFO] install_pkg.sh, current user: $(whoami)"

# install util packages
apt-get update
apt-get install -y vim
apt-get install -y git

# install NFS
if [ $4 = 'M' ]; then
  apt-get install nfs-kernel-server nfs-common -y
elif [ $4 = 'W' ]; then
  apt-get install nfs-common -y
fi

# docker, containerd 설치
apt-get update
apt-get install -y docker-ce=$2 docker-ce-cli=$2 containerd.io=$3

# 쿠버네티스 설치
apt-get update
apt-get install -y kubelet=$1 kubectl=$1 kubeadm=$1

# containerd configure to default
containerd config default >/etc/containerd/config.toml
sed -i 's/"cri"//' /etc/containerd/config.toml
systemctl restart containerd

# Fixed container runtime to containerd
cat <<EOF >/etc/default/kubelet
KUBELET_KUBEADM_ARGS=--container-runtime=remote \
                     --container-runtime-endpoint=/run/containerd/containerd.sock \
                     --cgroup-driver=systemd
EOF

# Avoid WARN&ERRO(default endpoints) when crictl run
cat <<EOF >/etc/crictl.yaml
runtime-endpoint: unix:///run/containerd/containerd.sock
image-endpoint: unix:///run/containerd/containerd.sock
EOF

# Ready to install for k8s
systemctl enable --now docker
systemctl enable --now containerd
systemctl enable --now kubelet
