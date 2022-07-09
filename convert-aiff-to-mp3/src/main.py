import os
import sys


def convertAfiiToMp3(path, filename):

    os.chdir(path)
    filename2, file_extention = os.path.splitext(filename)
    if file_extention == '.aiff':
        print("pwd=%s, path=%s, filename=%s" % (os.getcwd(), path, filename))
        print("ffmpeg -y -i %s -f mp3 -acodec libmp3lame -ab 320000 -ar 44100 %s.mp3" % (filename.replace(" ", "\\ "), filename2.replace(" ", "\\ ")))
        os.system("ffmpeg -y -i %s -f mp3 -acodec libmp3lame -ab 320000 -ar 44100 %s.mp3" % (filename.replace(" ", "\\ "), filename2.replace(" ", "\\ ")))


def main():
    os.chdir('/Users/kdj/Desktop/mp3')
    for (path, dir, files) in os.walk(os.getcwd()):
        for dirname in dir:
            full_filename = "%s/%s" % (path, dirname)
            print("path: %s" % full_filename)
        for filename in files:
            full_filename = "%s/%s" % (path, filename)
            convertAfiiToMp3(path, filename)


if __name__ == '__main__':
    main()
