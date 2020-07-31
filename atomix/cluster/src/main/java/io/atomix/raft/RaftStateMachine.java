/*
 * Copyright 2016-present Open Networking Foundation
 * Copyright © 2020 camunda services GmbH (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atomix.raft;

import io.atomix.utils.concurrent.ThreadContext;
import java.util.concurrent.CompletableFuture;

public interface RaftStateMachine extends AutoCloseable {

  /**
   * The state machine thread context; can be used to sequence operations on the state machine.
   *
   * @return the state machine thread context
   */
  ThreadContext executor();

  /**
   * Compacts Raft logs.
   *
   * @return a future to be completed once logs have been compacted
   */
  CompletableFuture<Void> compact();

  /**
   * Close any opened resources; note however that the thread context should NOT be closed, as it is
   * managed by the Raft server.
   */
  @Override
  void close();

  /**
   * Returns the current compactable index.
   *
   * @return the current compactable index
   */
  long getCompactableIndex();

  /**
   * Updates the compactable position; by default does nothing as the default behaviour is to use
   * the last applied index and term.
   *
   * @param index index up to which the log can be compacted
   */
  default void setCompactableIndex(final long index) {}
}
