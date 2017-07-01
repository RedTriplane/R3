
package com.jfixby.r3.ui;

import java.io.IOException;

import com.jfixby.r3.uiact.LoadTask;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.taskman.Job;
import com.jfixby.scarabei.api.taskman.Task;
import com.jfixby.scarabei.api.taskman.TaskManager;
import com.jfixby.scarabei.api.taskman.TaskProgress;
import com.jfixby.scarabei.red.taskman.RedSimpleProgress;

public class RedLoadTask implements LoadTask {

	public RedLoadTask (final Collection<ID> assets_to_load_list) {
		// assets_to_load_list.print("preload");
		this.progress.setTotal(assets_to_load_list.size());
		this.progress.updateProcessed(0);
		for (int i = 0; i < assets_to_load_list.size(); i++) {
			final RedLoadTaskStep step = new RedLoadTaskStep(assets_to_load_list.getElementAt(i));
			this.assets_to_load_list.add(step);
		}

	}

	class RedLoadTaskStep {
		private final ID scene_id;
		boolean procesed = false;

		public RedLoadTaskStep (final ID scene_id) {
			this.scene_id = scene_id;
		}

		public boolean isDone () {
			return this.procesed;
		}

		public void push () {
			try {
				AssetsManager.autoResolveAsset(this.scene_id);
				this.procesed = true;
			} catch (final IOException e) {
				e.printStackTrace();
				this.procesed = false;
			}
// this.procesed = true;
		}
	}

	// private long start;
	// private long DELTA = 1000;
	// private long end;
	private final Job update_progress_job = new Job() {

		@Override
		public void doStart () throws Throwable {
		}

		@Override
		public void doPush () throws Throwable {

			for (int i = 0; i < RedLoadTask.this.assets_to_load_list.size(); i++) {
				final RedLoadTaskStep step = RedLoadTask.this.assets_to_load_list.getElementAt(i);
				if (!step.isDone()) {
					step.push();
					break;
				}
			}

			int done = 0;
			for (int i = 0; i < RedLoadTask.this.assets_to_load_list.size(); i++) {
				final RedLoadTaskStep step = RedLoadTask.this.assets_to_load_list.getElementAt(i);
				if (step.isDone()) {
					done++;
				}
			}
			RedLoadTask.this.progress.updateProcessed(done);
			if (done == RedLoadTask.this.progress.getTotalSteps()) {
				RedLoadTask.this.progress.setIsDone();
			}

		}

		@Override
		public boolean isDone () {
			return RedLoadTask.this.progress.isDone();
		}
	};

	private final List<RedLoadTaskStep> assets_to_load_list = Collections.newList();

	final RedSimpleProgress progress = new RedSimpleProgress();

	@Override
	public void launch () {
		final Task task = TaskManager.newTask("update_progress_job", this.update_progress_job);
	}

	@Override
	public TaskProgress getProgress () {
		return this.progress;
	}

}
