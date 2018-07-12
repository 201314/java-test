
package com.gitee.linzl.file.event;

public class ProgressPublisher {

	public static void publishProgress(final ProgressListener listener, final ProgressEvent event) {
		if (listener == ProgressListener.NOOP || event == null) {
			return;
		}
		listener.progressChanged(event);
	}
}
