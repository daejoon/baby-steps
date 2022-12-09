#!/usr/bin/env bash

# 실행 유저 확인
echo "[INFO] install_pkg.sh, current user: $(whoami)"

# install util packages
rm -rfv /var/lib/apt/lists/*
apt-get update
apt-get install git -y

# install docker & k8s runtime(containerd)
apt-get install docker-ce=$2 docker-ce-cli=$2 containerd.io=$3 -y

# install kubernetes
# both kubelet and kubectl will install by dependency
# but aim to latest version. so fixed version by manually
apt-get install kubelet=$1 kubectl=$1 kubeadm=$1 -y

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
