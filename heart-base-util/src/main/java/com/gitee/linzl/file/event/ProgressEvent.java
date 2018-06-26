
package com.gitee.linzl.file.event;

import com.gitee.linzl.file.model.FileProgressPart;

public class ProgressEvent {
	private final ProgressEventType eventType;

	private final FileProgressPart part;

	public ProgressEvent(ProgressEventType eventType, FileProgressPart part) {
		this.eventType = eventType;
		this.part = part;
	}

	public ProgressEventType getEventType() {
		return eventType;
	}

	public FileProgressPart getPart() {
		return part;
	}

}
