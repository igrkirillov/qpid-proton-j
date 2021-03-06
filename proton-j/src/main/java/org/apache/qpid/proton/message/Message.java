/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.qpid.proton.message;

import org.apache.qpid.proton.amqp.messaging.ApplicationProperties;
import org.apache.qpid.proton.amqp.messaging.DeliveryAnnotations;
import org.apache.qpid.proton.amqp.messaging.Footer;
import org.apache.qpid.proton.amqp.messaging.Header;
import org.apache.qpid.proton.amqp.messaging.MessageAnnotations;
import org.apache.qpid.proton.amqp.messaging.Properties;
import org.apache.qpid.proton.amqp.messaging.Section;
import org.apache.qpid.proton.codec.ReadableBuffer;
import org.apache.qpid.proton.codec.WritableBuffer;
import org.apache.qpid.proton.message.impl.MessageImpl;

/**
 * Represents a Message within Proton.
 *
 * Create instances of Message using {@link Message.Factory}.
 *
 */
public interface Message
{

    public static final class Factory
    {
        public static Message create() {
            return new MessageImpl();
        }

        public static Message create(Header header,
                                     DeliveryAnnotations deliveryAnnotations,
                                     MessageAnnotations messageAnnotations,
                                     Properties properties,
                                     ApplicationProperties applicationProperties,
                                     Section body,
                                     Footer footer) {
            return new MessageImpl(header, deliveryAnnotations,
                                   messageAnnotations, properties,
                                   applicationProperties, body, footer);
        }
    }


    short DEFAULT_PRIORITY = 4;

    boolean isDurable();

    long getDeliveryCount();

    short getPriority();

    boolean isFirstAcquirer();

    long getTtl();

    void setDurable(boolean durable);

    void setTtl(long ttl);

    void setDeliveryCount(long deliveryCount);

    void setFirstAcquirer(boolean firstAcquirer);

    void setPriority(short priority);

    Object getMessageId();

    long getGroupSequence();

    String getReplyToGroupId();


    long getCreationTime();

    String getAddress();

    byte[] getUserId();

    String getReplyTo();

    String getGroupId();

    String getContentType();

    long getExpiryTime();

    Object getCorrelationId();

    String getContentEncoding();

    String getSubject();

    void setGroupSequence(long groupSequence);

    void setUserId(byte[] userId);

    void setCreationTime(long creationTime);

    void setSubject(String subject);

    void setGroupId(String groupId);

    void setAddress(String to);

    void setExpiryTime(long absoluteExpiryTime);

    void setReplyToGroupId(String replyToGroupId);

    void setContentEncoding(String contentEncoding);

    void setContentType(String contentType);

    void setReplyTo(String replyTo);

    void setCorrelationId(Object correlationId);

    void setMessageId(Object messageId);

    Header getHeader();

    DeliveryAnnotations getDeliveryAnnotations();

    MessageAnnotations getMessageAnnotations();

    Properties getProperties();

    ApplicationProperties getApplicationProperties();

    Section getBody();

    Footer getFooter();

    void setHeader(Header header);

    void setDeliveryAnnotations(DeliveryAnnotations deliveryAnnotations);

    void setMessageAnnotations(MessageAnnotations messageAnnotations);

    void setProperties(Properties properties);

    void setApplicationProperties(ApplicationProperties applicationProperties);

    void setBody(Section body);

    void setFooter(Footer footer);

    /**
     * TODO describe what happens if the data does not represent a complete message.
     * Currently this appears to leave the message in an unknown state.
     */
    int decode(byte[] data, int offset, int length);

    /**
     * Decodes the Message from the given {@link ReadableBuffer}.
     * <p>
     * If the buffer given does not contain the fully encoded Message bytes for decode
     * this method will throw an exception to indicate the buffer underflow condition and
     * the message object will be left in an undefined state.
     *
     * @param buffer
     *      A {@link ReadableBuffer} that contains the complete message bytes.
     */
    void decode(ReadableBuffer buffer);

    /**
     * Encodes up to {@code length} bytes of the message into the provided byte array,
     * starting at position {@code offset}.
     *
     * TODO describe what happens if length is smaller than the encoded form, Currently
     * Proton-J throws an exception. What does Proton-C do?
     *
     * @return the number of bytes written to the byte array
     */
    int encode(byte[] data, int offset, int length);

    /**
     * Encodes the current Message contents into the given {@link WritableBuffer} instance.
     * <p>
     * This method attempts to encode all message data into the {@link WritableBuffer} and
     * if the buffer has insufficient space it will throw an exception to indicate the buffer
     * overflow condition.  If successful the method returns the number of bytes written to
     * the provided buffer to fully encode the message.
     *
     * @param buffer
     *      The {@link WritableBuffer} instance to encode the message contents into.
     *
     * @return the number of bytes written to fully encode the message.
     */
    int encode(WritableBuffer buffer);

    void clear();

    MessageError getError();
}
